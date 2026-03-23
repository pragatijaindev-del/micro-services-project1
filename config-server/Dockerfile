
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY config-repo /app/config-repo
COPY target/*.jar app.jar
EXPOSE 8888
ENTRYPOINT ["java", "-jar", "app.jar"]