# Step 1: Build the application
FROM maven:3.9.6-eclipse-temurin-17 AS build
COPY . .
RUN mvn -f backend/pom.xml clean package -DskipTests

# Step 2: Run the application
FROM eclipse-temurin:17-jre-jammy
COPY --from=build /backend/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]