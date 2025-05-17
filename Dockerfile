# Stage 1: build using Maven + JDK 21
FROM maven:3.9.6-eclipse-temurin-21-alpine AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: run the built jar with JDK 21 runtime
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/target/kasmooi-invoice-1.0.0.jar app.jar
ENTRYPOINT ["java","-jar","/app/app.jar"]
