FROM openjdk:21
RUN groupadd -r app && useradd -r -g app app
RUN mkdir -p tournament-microservice
USER app

COPY build/libs/*.jar app.jar

COPY .env tournament-microservice/.env
ENTRYPOINT ["java","-jar","app.jar"]