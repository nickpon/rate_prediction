#!/bin/bash

# Deleting previous containers.

docker rm $(docker ps -a --format {{.ID}}::{{.Image}} | grep 'weather:2.1.6.RELEASE' | perl -lne 'print "$1" if /(.{12})(::)/')

docker rm $(docker ps -a --format {{.ID}}::{{.Image}} | grep 'rbc:2.1.6.RELEASE' | perl -lne 'print "$1" if /(.{12})(::)/')

docker rm $(docker ps -a --format {{.ID}}::{{.Image}} | grep 'prediction:2.1.6.RELEASE' | perl -lne 'print "$1" if /(.{12})(::)/')

docker rm $(docker ps -a --format {{.ID}}::{{.Image}} | grep 'hello:2.1.6.RELEASE' | perl -lne 'print "$1" if /(.{12})(::)/')

# Deleting previous images.

if docker images --format '{{.Repository}}:{{.Tag}}' | grep 'openjdk:8'
then
   echo "Deleting openjdk:8 image";
   docker rmi openjdk:8 -f
fi

if docker images --format '{{.Repository}}:{{.Tag}}' | grep 'weather:2.1.6.RELEASE'
then
   echo "Deleting weather:2.1.6.RELEASE image";
   docker rmi weather:2.1.6.RELEASE -f
fi

if docker images --format '{{.Repository}}:{{.Tag}}' | grep 'rbc:2.1.6.RELEASE'
then
   echo "Deleting rbc:2.1.6.RELEASE image";
   docker rmi rbc:2.1.6.RELEASE -f
fi

if docker images --format '{{.Repository}}:{{.Tag}}' | grep 'prediction:2.1.6.RELEASE'
then
   echo "Deleting prediction:2.1.6.RELEASE image";
   docker rmi prediction:2.1.6.RELEASE -f
fi

if docker images --format '{{.Repository}}:{{.Tag}}' | grep 'hello:2.1.6.RELEASE'
then
   echo "Deleting hello:2.1.6.RELEASE image";
   docker rmi hello:2.1.6.RELEASE -f
fi

# Docker building.

cd rbc
mvn package -DskipTests
docker build -t rbc:2.1.6.RELEASE .
cd ..

cd weather
mvn package
docker build -t weather:2.1.6.RELEASE .
cd ..

cd prediction
mvn package
docker build -t prediction:2.1.6.RELEASE .
cd ..

cd hello
mvn package
docker build -t hello:2.1.6.RELEASE .
cd ..

# Running app.

docker-compose up
