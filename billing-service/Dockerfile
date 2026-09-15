FROM eclipse-temurin:25-jdk AS build

WORKDIR /app
COPY billing-service/.mvn/ .mvn/
COPY billing-service/mvnw ./mvnw
COPY patient-api-contracts/pom.xml contracts/pom.xml
COPY patient-api-contracts/src/ contracts/src/
COPY billing-service/pom.xml billing-service/pom.xml
COPY billing-service/src/ billing-service/src/

RUN chmod +x mvnw && ./mvnw -f contracts/pom.xml clean install -DskipTests \
	&& ./mvnw -f billing-service/pom.xml clean package -DskipTests

FROM eclipse-temurin:25-jre

WORKDIR /app
RUN useradd --system --create-home spring
COPY --from=build /app/billing-service/target/*.jar app.jar
RUN chown spring:spring app.jar
USER spring

EXPOSE 4001 9001
ENTRYPOINT ["java", "-jar", "/app/app.jar"]