
FROM adoptopenjdk:11.0.10_9-jre-hotspot as builder
WORKDIR application
COPY target/app.jar app.jar
# Will unzip the jar to directories according to BOOT-INF/layers.idx
RUN java -Djarmode=layertools -jar app.jar extract

FROM adoptopenjdk:11.0.10_9-jre-hotspot
WORKDIR application
EXPOSE 8080
# Potential point of hard-to-find problems; as we hardcode layers found in layers.idx
COPY --from=builder application/dependencies/ ./
COPY --from=builder application/jackson/ ./
COPY --from=builder application/spring-boot-loader/ ./
COPY --from=builder application/snapshot-dependencies/ ./
COPY --from=builder application/utils/ ./
COPY --from=builder application/application/ ./
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]
