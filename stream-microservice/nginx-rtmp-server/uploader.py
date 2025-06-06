import requests
import sys
import os
from datetime import timedelta, datetime
from minio import Minio
from minio.error import S3Error

LOG_PATH = "/tmp/uploader.log"
RECORDINGS_PATH = "/var/recordings"
MINIO_HOST = "ec2-16-171-153-231.eu-north-1.compute.amazonaws.com:9000"
MINIO_ACCESS_KEY = "minioadmin"
MINIO_SECRET_KEY = "minioadmin"
MINIO_SECURE = False
BUCKET_NAME = "videos"
STREAM_MICROSERVICE_IP = "stream-microservice-service"
VIDEO_MICROSERVICE_IP = "video-microservice-service"
STREAMS_MANAGEMENT_PORT = 8085
VIDEO_MANAGEMENT_PORT = 8081
PRESIGNED_URL_EXPIRY_DAYS = 1

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
    file_path = os.path.join(RECORDINGS_PATH, filename)

    if not os.path.exists(file_path):
        log(f"❌ File not found: {file_path}")
        return

    log(f"🚀 Starting upload for: {file_path}")

    minio_client = Minio(
        MINIO_HOST,
        access_key=MINIO_ACCESS_KEY,
        secret_key=MINIO_SECRET_KEY,
        secure=MINIO_SECURE
    )

    try:
        stream_key = os.path.splitext(filename)[0]
        streams_url = f"http://{STREAM_MICROSERVICE_IP}:{STREAMS_MANAGEMENT_PORT}/streams-management/key/{stream_key}"
        response = requests.get(streams_url)

        if response.status_code != 200:
            log(f"❌ Failed to fetch stream metadata: {response.status_code}")
            return

        stream_data = response.json()

        if not minio_client.bucket_exists(BUCKET_NAME):
            minio_client.make_bucket(BUCKET_NAME)
            log(f"📦 Created bucket: {BUCKET_NAME}")

        object_name = f"{stream_data['channelId']}/{filename}"
        minio_client.fput_object(BUCKET_NAME, object_name, file_path)
        log(f"✅ Uploaded to MinIO: {stream_data['channelId']}")

        url = minio_client.presigned_get_object(
            BUCKET_NAME,
            object_name,
            expires=timedelta(days=PRESIGNED_URL_EXPIRY_DAYS)
        )
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

        backend_url = f"http://{VIDEO_MICROSERVICE_IP}:{VIDEO_MANAGEMENT_PORT}/videos"
        post_response = requests.post(backend_url, json=video_payload)

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
