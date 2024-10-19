# Use an official OpenJDK runtime as the base image
FROM openjdk:17-jdk-slim

RUN apt-get update && \
    apt-get install -y python3 python3-pip && \
    rm -rf /var/lib/apt/lists/*

RUN pip3 install --no-cache-dir transformers torch

WORKDIR /app

COPY back/target/*.jar app.jar
COPY back/src ./src

# Expose the port the application runs on
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "app.jar"]
