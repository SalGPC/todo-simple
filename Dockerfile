# =========================
# 1️⃣ Build stage
# =========================
FROM maven:3.9.9-eclipse-temurin-21 AS build

WORKDIR /app

# Copy only pom.xml first (for caching dependencies)
COPY pom.xml .

# Download dependencies (cache layer)
RUN mvn dependency:go-offline -B

# Copy the rest of the source code
COPY src ./src

# Build the JAR file (skip tests for speed)
RUN mvn clean package -DskipTests

# =========================
# 2️⃣ Runtime stage
# =========================
FROM eclipse-temurin:21-jdk-jammy

WORKDIR /app

# Copy JAR from builder
COPY --from=build /app/target/*.jar app.jar

# Expose the app port
EXPOSE 8080

# Set Spring profile (so you can override in docker-compose if needed)
ENV SPRING_PROFILES_ACTIVE=docker

ENTRYPOINT ["java", "-jar", "app.jar"]
