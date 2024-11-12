FROM openjdk:23 AS build

RUN apt-get update && apt-get install -y maven

COPY pom.xml ./
COPY src src
RUN mvn dependency:resolve
RUN mvn package

FROM openjdk:23
WORKDIR /demo

EXPOSE 8080

COPY --from=build target/*.jar demo.jar
COPY src/main/resources/static/costs_export.csv /demo/costs_export.csv

ENTRYPOINT ["java", "-jar", "demo.jar"]