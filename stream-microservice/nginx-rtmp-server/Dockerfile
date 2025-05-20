FROM ubuntu:20.04

ENV DEBIAN_FRONTEND=noninteractive
RUN apt update && \
    apt install -y tzdata && \
    ln -fs /usr/share/zoneinfo/Etc/UTC /etc/localtime && \
    dpkg-reconfigure --frontend noninteractive tzdata

RUN apt install -y nginx-core libnginx-mod-rtmp python3 python3-pip curl && \
    pip3 install minio requests && \
    apt clean && \
    rm -rf /var/lib/apt/lists/*

COPY nginx.conf /etc/nginx/nginx.conf
COPY uploader.py /opt/uploader.py

RUN mkdir -p /var/recordings
RUN chown www-data:www-data /var/recordings
RUN chmod +x /opt/uploader.py
RUN chmod 755 /var/recordings

EXPOSE 80 1935

CMD ["nginx", "-g", "daemon off;"]
