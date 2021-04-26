FROM docker.io/library/adoptopenjdk:11.0.10_9-jdk-hotspot
COPY target/remote-debugging-0.0.1-SNAPSHOT.jar app.jar
#Spring boot
EXPOSE 8080
#Debugger
EXPOSE 5005
#JMX
EXPOSE 9010
#TODO: Create entrypoint which allows to change such data
ENTRYPOINT export JAVA_TOOL_OPTIONS="\
        -agentlib:jdwp=transport=dt_socket,server=y,suspend=${JAVA_DEBUG_SUSPEND:-n},address=${JAVA_DEBUG_PORT:-*:5005} \
        -Dcom.sun.management.jmxremote.ssl=false \
        -Dcom.sun.management.jmxremote.authenticate=false \
        -Dcom.sun.management.jmxremote.port=${JAVA_RMX_PORT:-5000} \
        -Dcom.sun.management.jmxremote.rmi.port=${JAVA_RMX_PORT:-5000} \
        -Djava.rmi.server.hostname=${JAVA_RMI_HOSTNAME:-0.0.0.0} \
        -Dcom.sun.management.jmxremote.host=${JAVA_JMX_HOSTNAME:-0.0.0.0} \
        -Dcom.sun.management.jmxremote.local.only=false" \
    && java -jar app.jar
