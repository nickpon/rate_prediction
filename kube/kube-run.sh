#!/bin/bash

minikube delete
minikube start
kubectl config use-context minikube

# Just to check that docker is connected.

eval "$(minikube docker-env)"

# Docker building.

cd ../services/rbc || exit
rm -rf target/
mvn package -DskipTests
docker build -t rbc:2.1.6.RELEASE .

cd ../weather || exit
rm -rf target/
mvn package
docker build -t weather:2.1.6.RELEASE .

cd ../prediction || exit
rm -rf target/
mvn package
docker build -t prediction:2.1.6.LOCAL  -f Dockerfile.local .

cd ../hello || exit
rm -rf target/
mvn package
docker build -t hello:2.1.6.RELEASE .

cd ../../

# Create postgres pods, services and persistent volumes with configurations given.

cd kube/ || exit

kubectl create -f postgres.yaml
kubectl create -f deployment-hello.yaml
kubectl create -f deployment-weather.yaml
kubectl create -f deployment-rbc.yaml
kubectl create -f deployment-prediction.yaml

# Print urls to access services.

echo "Hello service"
minikube service hello --url

echo "Weather service"
minikube service weather --url

echo "RBC service"
minikube service rbc --url

echo "Prediction service"
minikube service prediction --url

echo "Minikube dashboard"
minikube dashboard --url=true
