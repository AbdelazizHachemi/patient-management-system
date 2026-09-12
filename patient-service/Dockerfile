FROM eclipse-temurin:25-jdk AS build

WORKDIR /app
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./
COPY src/ src/

RUN chmod +x mvnw && ./mvnw clean package -DskipTests

FROM eclipse-temurin:25-jre

WORKDIR /app
RUN useradd --system --create-home spring
COPY --from=build /app/target/*.jar app.jar
RUN chown spring:spring app.jar
USER spring

EXPOSE 4000
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
