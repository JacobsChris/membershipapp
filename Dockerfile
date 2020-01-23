FROM maven:latest

COPY . /build

WORKDIR /build

RUN mvn clean package -Dskiptests

FROM java:8

WORKDIR /opt/membershipapp

COPY --from=0 /build/target/membershipapp.jar app.jar

ENTRYPOINT ["/usr/bin/java", "-jar", "app.jar"]