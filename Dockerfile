FROM ubuntu:latest AS build

COPY . .

RUN apt-get update
RUN apt-get install openjdk-17-jdk -y

FROM openjdk:17-jdk-slim

RUN apt-get install maven -y
RUN mvn clean install

COPY --from=build target/todo-1.0.0.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
