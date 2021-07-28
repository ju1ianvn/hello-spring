# Execute app
FROM bellsoft/liberica-openjdk-alpine-musl:11.0.12-7-x86_64
WORKDIR /opt/hello-spring
COPY .build/libs/hello-spring-0.0.1-SNAPSHOT.jar ./
CMD ["java", "-jar", "./hello-spring-0.0.1-SNAPSHOT.jar"]
