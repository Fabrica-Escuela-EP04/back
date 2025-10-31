# Building phase
FROM maven:latest AS build 
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Packing phase (runtime)
FROM openjdk:17-jdk-slim
WORKDIR /app
# Copy JAR file from build phase to current phase
COPY --from=build /app/target/med-office.jar med-office.jar
EXPOSE 8080
# Execution 
ENTRYPOINT ["java","-jar","med-office.jar"]