FROM eclipse-temurin:25-jdk AS build

WORKDIR /app
COPY auth-service/.mvn/ .mvn/
COPY auth-service/mvnw ./mvnw
COPY auth-service/pom.xml auth-service/pom.xml
COPY auth-service/src/ auth-service/src/

RUN chmod +x mvnw && ./mvnw -f auth-service/pom.xml clean package -DskipTests

FROM eclipse-temurin:25-jre

WORKDIR /app
RUN useradd --system --create-home spring
COPY --from=build /app/auth-service/target/*.jar app.jar
RUN chown spring:spring app.jar
USER spring

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
