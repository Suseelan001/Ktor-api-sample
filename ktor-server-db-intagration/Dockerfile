 
# Use OpenJDK as the base image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file from the build output to the container
COPY build/libs/your-ktor-app.jar app.jar

# Expose the port your Ktor application runs on
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "app.jar"]
