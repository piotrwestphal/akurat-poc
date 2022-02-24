FROM openjdk:11-slim AS BUILD_IMAGE
WORKDIR /usr/app/
ADD . .
RUN ./gradlew :app:shadowJar

FROM openjdk:11-jre-slim
WORKDIR /app
COPY --from=BUILD_IMAGE /usr/app/build/libs/*.jar app.jar
ENTRYPOINT ["java" , "-jar", "app.jar"]
EXPOSE 8080