FROM maven:3.6.0-jdk-11 AS builder
RUN mkdir -p /app/ski

WORKDIR /app/ski
ADD . /app/ski/

RUN mvn clean package
RUN ls -la /app/ski/server/target/

FROM adoptopenjdk/openjdk11:jre-11.0.3_7-alpine
RUN mkdir -p /app
COPY --from=builder /app/ski/server/target/*.jar /app/app.jar
VOLUME /tmp
WORKDIR /app
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app/app.jar"]
