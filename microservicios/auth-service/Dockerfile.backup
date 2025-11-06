FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /build
COPY ../pom.xml ./pom.xml
COPY pom.xml ./auth-service/pom.xml
COPY src ./auth-service/src
WORKDIR /build/auth-service
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /build/auth-service/target/*.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]
