# Generate build
FROM amazoncorretto:11.0.12-alpine AS builder
WORKDIR /tmp/hello-gradle/
COPY . .
RUN ./gradlew assemble

# Execute app
FROM bellsoft/liberica-openjdk-alpine-musl:11.0.12-7-x86_64
WORKDIR /opt/hello-gradle
COPY --from=builder /tmp/hello-gradle/build/libs/hello-spring-0.0.1-SNAPSHOT.jar ./
CMD ["java", "-jar", "./hello-spring-0.0.1-SNAPSHOT.jar"]
