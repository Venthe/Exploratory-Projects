FROM docker.io/library/adoptopenjdk:${ADOPTOPENJDK_VERSION:-11.0.3_7-jdk-hotspot} as build
WORKDIR /temp
COPY target/mvn-supplementary-plugins-javadoc.jar javadoc.jar
RUN jar -xf javadoc.jar && rm javadoc.jar

FROM docker.io/library/nginx:${NGINX_VERSION:-1.19-alpine}
COPY --from=build /temp/ /usr/share/nginx/html
