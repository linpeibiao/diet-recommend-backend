FROM openjdk:8

RUN mkdir /usr/myapp

COPY target/diet_recommend-1.0-SNAPSHOT.jar /usr/myapp

WORKDIR /usr/myapp

ENTRYPOINT ["java", "-jar", "-Xmx256m", "diet_recommend-1.0-SNAPSHOT.jar"]