# Use OpenJDK as the base image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

ARG JAR_FILE=build/libs/ktor-server-db-intagration-all.jar
COPY ${JAR_FILE} /app/

# Expose the port your Ktor application runs on
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "ktor-server-db-intagration-all.jar"]