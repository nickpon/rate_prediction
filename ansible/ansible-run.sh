#!/bin/bash
# shellcheck disable=SC1083

# Deleting previous containers.

docker ps -a --format {{.ID}}::{{.Image}} | grep 'weather:2.1.6.RELEASE' | perl -lne 'print "$1" if /(.{12})(::)/' | while IFS='' read -r line
do
  docker rm "$line"
done

docker ps -a --format {{.ID}}::{{.Image}} | grep 'rbc:2.1.6.RELEASE' | perl -lne 'print "$1" if /(.{12})(::)/' | while IFS='' read -r line
do
  docker rm "$line"
done

docker ps -a --format {{.ID}}::{{.Image}} | grep 'prediction:2.1.6.WEB' | perl -lne 'print "$1" if /(.{12})(::)/' | while IFS='' read -r line
do
  docker rm "$line"
done

docker ps -a --format {{.ID}}::{{.Image}} | grep 'hello:2.1.6.RELEASE' | perl -lne 'print "$1" if /(.{12})(::)/' | while IFS='' read -r line
do
  docker rm "$line"
done

docker ps -a --format {{.ID}}::{{.Image}} | grep 'dpage/pgadmin4' | perl -lne 'print "$1" if /(.{12})(::)/' | while IFS='' read -r line
do
  docker rm "$line"
done

docker ps -a --format {{.ID}}::{{.Image}} | grep 'postgres' | perl -lne 'print "$1" if /(.{12})(::)/' | while IFS='' read -r line
do
  docker rm "$line"
done


# Deleting previous images.

if docker images --format '{{.Repository}}:{{.Tag}}' | grep 'openjdk:8'
then
   echo "Deleting openjdk:8 image.";
   docker rmi openjdk:8 -f
fi

if docker images --format '{{.Repository}}:{{.Tag}}' | grep 'postgres:latest'
then
   echo "Deleting postgres:latest image.";
   docker rmi postgres:latest -f
fi

if docker images --format '{{.Repository}}:{{.Tag}}' | grep 'dpage/pgadmin4:latest'
then
   echo "Deleting dpage/pgadmin4:latest image.";
   docker rmi dpage/pgadmin4:latest -f
fi

if docker images --format '{{.Repository}}:{{.Tag}}' | grep 'weather:2.1.6.RELEASE'
then
   echo "Deleting weather:2.1.6.RELEASE image.";
   docker rmi weather:2.1.6.RELEASE -f
fi

if docker images --format '{{.Repository}}:{{.Tag}}' | grep 'rbc:2.1.6.RELEASE'
then
   echo "Deleting rbc:2.1.6.RELEASE image.";
   docker rmi rbc:2.1.6.RELEASE -f
fi

if docker images --format '{{.Repository}}:{{.Tag}}' | grep 'prediction:2.1.6.WEB'
then
   echo "Deleting prediction:2.1.6.WEB image.";
   docker rmi prediction:2.1.6.WEB -f
fi

if docker images --format '{{.Repository}}:{{.Tag}}' | grep 'hello:2.1.6.RELEASE'
then
   echo "Deleting hello:2.1.6.RELEASE image.";
   docker rmi hello:2.1.6.RELEASE -f
fi


# Docker building.

cd ../services/rbc || exit
rm -rf rbc:2.1.6.RELEASE.tar
rm -rf target/
mvn package -DskipTests
docker build -t rbc:2.1.6.RELEASE .
docker save rbc:2.1.6.RELEASE > rbc:2.1.6.RELEASE.tar

cd ../weather || exit
rm -rf weather:2.1.6.RELEASE.tar
rm -rf target/
mvn package
docker build -t weather:2.1.6.RELEASE .
docker save weather:2.1.6.RELEASE > weather:2.1.6.RELEASE.tar

cd ../prediction || exit
rm -rf prediction:2.1.6.RELEASE.tar
rm -rf target/
mvn package
docker build -t prediction:2.1.6.WEB  -f Dockerfile.web .
docker save prediction:2.1.6.WEB > prediction:2.1.6.WEB.tar

cd ../hello || exit
rm -rf hello:2.1.6.RELEASE.tar
rm -rf target/
mvn package
docker build -t hello:2.1.6.RELEASE .
docker save hello:2.1.6.RELEASE > hello:2.1.6.RELEASE.tar

# Running app.

cd ../../ansible/ || exit
ansible-playbook deploy.yml --ask-vault-pass -v
