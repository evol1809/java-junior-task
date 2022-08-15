FROM openjdk:11
WORKDIR /java-junior-task
COPY target/java-junior-task-0.0.1-SNAPSHOT.jar ./
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "java-junior-task-0.0.1-SNAPSHOT.jar", "--spring.config.name=application-postgresql"]
