# Use an official OpenJDK runtime as the base image
FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/*.jar app.jar

# Expose the port the application runs on
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "app.jar"]
