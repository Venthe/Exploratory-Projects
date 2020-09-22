ARG BUILD_PATH=/usr/src
ARG APP_PATH=/usr/app

FROM maven:3.6.3-openjdk-11 as base
LABEL image=dependencies
ARG BUILD_PATH
ARG APP_PATH
WORKDIR ${BUILD_PATH}
ADD pom.xml pom.xml
RUN mvn dependency:go-offline

FROM dependencies as sources
ARG BUILD_PATH
LABEL image=sources
ARG APP_PATH
ADD ./ ./

FROM sources as test
ARG BUILD
LABEL build=${build}
LABEL image=test

RUN mvn test 2>&1 | tee mvn-test.log

FROM sources as build
LABEL image=build
ARG BUILD_PATH
ARG APP_PATH
RUN mvn package -DskipTests 2>&1 | tee mvn-package.log

FROM openjdk:11
ARG BUILD_PATH
ARG APP_PATH
WORKDIR ${APP_PATH}
COPY --from=build "${BUILD_PATH}/target/app.jar" "app.jar"
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "./app.jar"]