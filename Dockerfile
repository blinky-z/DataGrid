FROM openjdk:11-jdk-slim
WORKDIR /app
COPY build/libs/*.jar /app/datagrid.jar

ENTRYPOINT ["java", "-jar", "/app/datagrid.jar"]
