FROM gradle:8.4-jdk17-alpine AS build
WORKDIR /app

COPY build.gradle.kts settings.gradle.kts /app/

COPY src /app/src

RUN gradle build -x test --no-daemon

FROM openjdk:17-jdk-alpine
WORKDIR /app

RUN addgroup -S appgroup && adduser -S appuser -G appgroup

COPY --from=build /app/build/libs/*.jar app.jar

USER appuser:appgroup

ENTRYPOINT ["java", "-jar", "/app/app.jar"]

##test