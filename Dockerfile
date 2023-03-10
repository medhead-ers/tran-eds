FROM eclipse-temurin:19-jdk-alpine
VOLUME /tmp
COPY target/*.jar app.jar
ENTRYPOINT ["java","-Dspring.profiles.active=docker", "-jar","/app.jar"]