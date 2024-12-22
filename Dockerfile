# Build Stage
FROM maven:3.8.5-openjdk-17 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy all the files from the current directory on your local machine to /app in the container
COPY . .

# Run Maven to clean and build the JAR file, skipping tests
RUN mvn clean package -DskipTests

# Runtime Stage
FROM openjdk:17.0.1-jdk-slim

# Set the working directory inside the runtime container
WORKDIR /app

# Copy the JAR file from the build stage into the runtime container
COPY --from=build /app/target/dummy-0.0.1-SNAPSHOT.jar /app/dummy.jar

# Expose the port your Spring Boot app will run on (default is 8080)
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "/app/dummy.jar"]
