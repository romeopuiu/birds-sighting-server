FROM openjdk:11-jre-slim
WORKDIR /app
COPY target/birds-sighting-server.jar /app
CMD ["java", "-jar", "birds-sighting-server.jar"]