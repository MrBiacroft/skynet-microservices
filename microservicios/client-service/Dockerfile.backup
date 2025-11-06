FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /build
COPY ../pom.xml ./pom.xml
COPY pom.xml ./client-service/pom.xml
COPY src ./client-service/src
WORKDIR /build/client-service
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /build/client-service/target/*.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "app.jar"]
