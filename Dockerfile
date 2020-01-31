FROM maven:latest

COPY . /build

WORKDIR /build

RUN rm -rf src/main/resources/static

RUN mvn clean package -Dskiptests

FROM java:8

WORKDIR /opt/membershipapp

COPY --from=0 /build/target/membershipapp-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["/usr/bin/java", "-jar", "app.jar"]