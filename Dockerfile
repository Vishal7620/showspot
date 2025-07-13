# Use Maven image to build the app
FROM maven:3.9.6-eclipse-temurin-17 AS builder
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Use a lighter JDK image to run the app
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=builder /app/target/dummy-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
