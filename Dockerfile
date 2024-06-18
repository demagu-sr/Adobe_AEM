FROM maven:3.8.5-openjdk-17 AS builder

# Copy the project's POM file and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the source code and build the application
COPY src/ ./src/
RUN mvn clean package

# Create the final image
FROM openjdk:17-jdk-slim
COPY --from=builder target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
LABEL authors="srikar demagu"