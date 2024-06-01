FROM openjdk:17-oracle

COPY ./global.solution/target/global.solution-0.0.1-SNAPSHOT.jar app.jar
RUN mkdir files
CMD ["java", "-jar", "app.jar"]