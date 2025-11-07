FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /build
COPY microservicios/auth-service/pom.railway.xml ./pom.xml
COPY microservicios/auth-service/src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /build/target/auth-service-1.0.0.jar app.jar
EXPOSE 8081
CMD java -jar app.jar
