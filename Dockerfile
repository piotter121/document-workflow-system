FROM maven:3.6.0-jdk-11 as builder

RUN mkdir --parents /usr/src/app
WORKDIR /usr/src/app
COPY pom.xml /usr/src/app/
RUN mvn dependency:go-offline -P docker

ARG skipTests=false
COPY src /usr/src/app/src/
RUN mvn -DskipTests=${skipTests} clean package -P docker

FROM openjdk:11-jre-slim
COPY --from=builder /usr/src/app/target/dependency/BOOT-INF/lib /app/lib
COPY --from=builder /usr/src/app/target/dependency/META-INF /app/META-INF
COPY --from=builder /usr/src/app/target/dependency/BOOT-INF/classes /app

VOLUME /tmp
EXPOSE 8080

ENTRYPOINT ["java","-cp","app:app/lib/*", "-Dspring.profiles.active=docker","pl.edu.pw.ee.pyskp.documentworkflow.DocumentWorkflowSystemApplication"]