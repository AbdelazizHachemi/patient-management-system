FROM eclipse-temurin:25-jdk AS build

WORKDIR /app
COPY api-gateway/.mvn/ .mvn/
COPY api-gateway/mvnw ./mvnw
COPY api-gateway/pom.xml api-gateway/pom.xml
COPY api-gateway/src/ api-gateway/src/

RUN chmod +x mvnw && ./mvnw -f api-gateway/pom.xml clean package -DskipTests

FROM eclipse-temurin:25-jre

WORKDIR /app
RUN useradd --system --create-home spring
COPY --from=build /app/api-gateway/target/*.jar app.jar
RUN chown spring:spring app.jar
USER spring

EXPOSE 4004
ENTRYPOINT ["java", "-jar", "/app/app.jar"]