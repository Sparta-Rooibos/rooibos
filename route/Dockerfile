FROM --platform=linux/amd64 eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY build/libs/*SNAPSHOT.jar app.jar

EXPOSE 19101

ENTRYPOINT ["java", "-jar", "app.jar"]