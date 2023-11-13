FROM openjdk:8

RUN mkdir /usr/myapp
RUN touch /home/code/diet-recommend/log/backend.log

COPY target/diet-recommend-backend-1.0-SNAPSHOT.jar /usr/myapp

WORKDIR /usr/myapp

ENTRYPOINT ["java", "-jar", "-Xmx256m", "diet-recommend-backend-1.0-SNAPSHOT.jar  >> /home/code/diet-recommend/log/backend.log 2>&1 &"]