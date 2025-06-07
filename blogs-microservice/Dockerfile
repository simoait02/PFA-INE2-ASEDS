FROM gradle:8.4-jdk21-alpine AS build
WORKDIR /app

COPY build.gradle.kts settings.gradle.kts /app/

COPY src /app/src

RUN gradle build -x test --no-daemon

FROM openjdk:21
WORKDIR /app

RUN groupadd -r appgroup && useradd -r -g appgroup appuser

COPY --from=build /app/build/libs/*.jar app.jar

USER appuser:appgroup

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
