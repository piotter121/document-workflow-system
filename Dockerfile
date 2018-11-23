FROM maven:3.5.2-jdk-8 as builder

RUN mkdir --parents /usr/src/app
WORKDIR /usr/src/app
COPY pom.xml /usr/src/app/
RUN mvn dependency:go-offline -P docker-build

ARG skipTests=true
COPY src /usr/src/app/src/
RUN mvn -DskipTests=${skipTests} clean package -P docker-build

FROM openjdk:8-jre-alpine
COPY --from=builder /usr/src/app/target/dependency/BOOT-INF/lib /app/lib
COPY --from=builder /usr/src/app/target/dependency/META-INF /app/META-INF
COPY --from=builder /usr/src/app/target/dependency/BOOT-INF/classes /app

VOLUME /tmp
EXPOSE 8080

ENTRYPOINT ["java","-cp","app:app/lib/*","pl.edu.pw.ee.pyskp.documentworkflow.DocumentWorkflowSystemApplication"]