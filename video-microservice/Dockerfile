FROM openjdk:21
RUN groupadd -r app && useradd -r -g app app
RUN mkdir -p video-microservice
USER app

COPY build/libs/*.jar app.jar

COPY .env video-microservice/.env
ENTRYPOINT ["java","-jar","app.jar"]
