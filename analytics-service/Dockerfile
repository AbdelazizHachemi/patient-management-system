FROM eclipse-temurin:25-jdk AS build

WORKDIR /app
COPY analytics-service/.mvn/ .mvn/
COPY analytics-service/mvnw ./mvnw
COPY patient-api-contracts/pom.xml contracts/pom.xml
COPY patient-api-contracts/src/ contracts/src/
COPY analytics-service/pom.xml analytics-service/pom.xml
COPY analytics-service/src/ analytics-service/src/

RUN chmod +x mvnw && ./mvnw -f contracts/pom.xml clean install -DskipTests \
	&& ./mvnw -f analytics-service/pom.xml clean package -DskipTests

FROM eclipse-temurin:25-jre

WORKDIR /app
RUN useradd --system --create-home spring
COPY --from=build /app/analytics-service/target/*.jar app.jar
RUN chown spring:spring app.jar
USER spring

EXPOSE 4002
ENTRYPOINT ["java", "-jar", "/app/app.jar"]