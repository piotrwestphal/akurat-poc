FROM gradle:7-jdk11 AS BUILD_IMAGE
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle :app:shadowJar --no-daemon

FROM openjdk:11-jre-slim
EXPOSE 8080:8080
WORKDIR /app
COPY --from=BUILD_IMAGE /home/gradle/src/app/build/libs/*.jar app.jar
ENTRYPOINT ["java" , "-jar", "app.jar"]
