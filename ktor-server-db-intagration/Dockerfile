FROM openjdk:23-jdk-slim

WORKDIR /app

# Copy application.yaml & app jar
COPY src/main/resources/application.yaml /app/resources/application.yaml
COPY build/libs/ktor-server-db-intagration-all.jar /app/

# Expose port
EXPOSE 8080

# Run Ktor with explicit config file
CMD ["java", "-Dconfig.file=/app/resources/application.yaml", "-jar", "/app/ktor-server-db-intagration-all.jar"]
