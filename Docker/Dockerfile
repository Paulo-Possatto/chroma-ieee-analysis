FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/chroma-rogers-analysis-*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]