#!/bin/bash
git stash save
git pull
mvn clean package -DskipTests=true
docker stop diet-recommend
docker rm diet-recommend
docker image rm diet-recommend:v1.0.0
docker build . -f Dockerfile -t diet-recommend:v1.0.0
docker run -d --name diet-recommend -p 8085:8888 diet-recommend:v1.0.0