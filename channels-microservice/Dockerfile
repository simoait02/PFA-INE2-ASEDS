# BUILD STEP
FROM gradle:8.4-jdk17-alpine AS build
WORKDIR /app

COPY build.gradle.kts settings.gradle.kts /app/
COPY src /app/src

RUN gradle dependencies --no-daemon

RUN gradle build -x test --no-daemon

# EXECUTION STEP
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

RUN addgroup --system appgroup && adduser --system --ingroup appgroup appuser

COPY --from=build /app/build/libs/*.jar app.jar

RUN chown appuser:appgroup app.jar

USER appuser

ENTRYPOINT ["java", "-jar", "/app/app.jar"]

##secure image
