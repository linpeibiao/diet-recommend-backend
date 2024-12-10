FROM openjdk:8

RUN mkdir /usr/myapp
RUN mkdir /home/log
RUN touch /home/log/backend.log

COPY target/diet-recommend-backend-1.0-SNAPSHOT.jar /usr/myapp

WORKDIR /usr/myapp

ENTRYPOINT ["java", "-jar", "-Xmx512m", "diet-recommend-backend-1.0-SNAPSHOT.jar"]