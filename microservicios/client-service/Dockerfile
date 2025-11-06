FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /build
COPY microservicios/client-service/pom.railway.xml ./pom.xml
COPY microservicios/client-service/src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /build/target/client-service-1.0.0.jar app.jar
EXPOSE 8082
CMD java -jar app.jar
