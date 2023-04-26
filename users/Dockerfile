FROM --platform=linux/amd64 openjdk:17-jdk-alpine
COPY build/libs/*.jar /users.jar
ENTRYPOINT ["java", "-jar", "/users.jar"]
