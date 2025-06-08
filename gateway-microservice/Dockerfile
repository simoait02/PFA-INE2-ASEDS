FROM openjdk:17
RUN groupadd -r app && useradd -r -g app app
RUN mkdir -p gateway-microservice
USER app

COPY build/libs/*.jar app.jar

COPY .env gateway-microservice/.env
ENTRYPOINT ["java","-jar","app.jar"]