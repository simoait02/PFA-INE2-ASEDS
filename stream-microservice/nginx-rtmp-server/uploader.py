#!/usr/bin/env python3

import requests
import sys
import os
from datetime import timedelta, datetime
from minio import Minio
from minio.error import S3Error

LOG_PATH = "/tmp/uploader.log"

def log(message):
    with open(LOG_PATH, "a") as f:
        f.write(message + "\n")

def calculate_duration(start, end):
    try:
        fmt = "%Y-%m-%dT%H:%M:%S.%f"
        start_dt = datetime.strptime(start, fmt)
        end_dt = datetime.strptime(end, fmt)
        duration = int((end_dt - start_dt).total_seconds())
        return max(duration, 0)
    except Exception as e:
        log(f"⚠️ Error parsing duration: {str(e)}")
        return 0

def main():
    if len(sys.argv) < 2:
        log("❌ No filename provided")
        return

    filename = sys.argv[1]
    file_path = f"/var/recordings/{filename}"

    if not os.path.exists(file_path):
        log(f"❌ File not found: {file_path}")
        return

    log(f"🚀 Starting upload for: {file_path}")

    minio_client = Minio(
        "minio:9000",
        access_key="minioadmin",
        secret_key="minioadmin",
        secure=False
    )

    bucket_name = "videos"
    host_ip = "192.168.59.165"

    try:
        stream_key = os.path.splitext(filename)[0]
        response = requests.get(f"http://{host_ip}:8085/streams-management/key/{stream_key}")
        if response.status_code != 200:
            log(f"❌ Failed to fetch stream metadata: {response.status_code}")
            return

        stream_data = response.json()

        if not minio_client.bucket_exists(bucket_name):
            minio_client.make_bucket(bucket_name)
            log(f"📦 Created bucket: {bucket_name}")

        object_name = f"{stream_data['channelId']}/{filename}"
        minio_client.fput_object(bucket_name, object_name, file_path)
        log(f"✅ Uploaded to MinIO: {stream_data['channelId']}")

        url = minio_client.presigned_get_object(bucket_name, object_name, expires=timedelta(days=1))
        log(f"🎬 Pre-signed URL: {url}")

        start = stream_data.get("startTime")
        end = stream_data.get("endTime")
        duration = calculate_duration(start, end) if start and end else 0

        video_payload = {
            "title": stream_data["title"],
            "description": stream_data["description"],
            "channelId": stream_data["channelId"],
            "uploadDate": datetime.utcnow().isoformat(),
            "duration": duration,
            "resolutions": ["1080p"],
            "videoUrl": url,
            "views": stream_data.get("viewCount", 0),
            "likes": stream_data.get("likesCount", 0),
            "comments": stream_data.get("comments", []),
            "tags": stream_data.get("tags", [])
        }

        post_response = requests.post(f"http://{host_ip}:8081/videos", json=video_payload)
        if post_response.status_code == 201:
            log("📥 Video metadata successfully saved to backend.")
        else:
            log(f"❌ Failed to save video metadata: {post_response.status_code} - {post_response.text}")

    except S3Error as e:
        log(f"❌ Upload error: {str(e)}")
    except Exception as e:
        log(f"❌ General error: {str(e)}")

if __name__ == "__main__":
    main()
