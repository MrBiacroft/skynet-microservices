FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /build
COPY ../pom.xml ./pom.xml
COPY pom.xml ./visit-service/pom.xml
COPY src ./visit-service/src
WORKDIR /build/visit-service
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /build/visit-service/target/*.jar app.jar
EXPOSE 8083
ENTRYPOINT ["java", "-jar", "app.jar"]
