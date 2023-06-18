FROM eclipse-temurin:20.0.1_9-jdk-alpine
LABEL authors="senna"
ARG JAR_FILE=*.jar
COPY ${JAR_FILE} application.jar

ENTRYPOINT ["java", "-jar", "application.jar"]
