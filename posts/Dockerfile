FROM --platform=linux/amd64 openjdk:17-jdk-alpine
COPY build/libs/*.jar /posts.jar
ENTRYPOINT ["java", "-jar", "/posts.jar"]
