FROM adoptopenjdk:11-jre-hotspot
VOLUME /tmp
COPY build/libs/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

