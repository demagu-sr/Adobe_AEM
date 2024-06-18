# Build the application using maven
FROM maven:3.8.5-openjdk-17 as builder

# set working directory inside the container
WORKDIR /app

# Copy pom.xml and source code
COPY pom.xml /app
COPY src/ /app/src/

# Package the application using maven
RUN mvn clean package

FROM openjdk:17-jdk-alpine
WORKDIR /app

# Copy the packaged jar file
COPY --from=builder /app/target/Adobe-0.0.2-SNAPSHOT.jar app.jar

# Export the port that application runs on
EXPOSE 8080

# Set the entry point for the jar file to run
ENTRYPOINT ["java","-jar","app.jar"]
LABEL authors="srikar demagu"
