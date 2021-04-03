FROM adoptopenjdk:11.0.10_9-jre-hotspot
ADD target/app.jar /app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
