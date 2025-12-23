FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app
COPY config-repo /app/config-repo
COPY target/*.jar app.jar
EXPOSE 8888
ENTRYPOINT ["java", "-jar", "app.jar"]