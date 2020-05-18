# Deployment under kubernetes.

1. Running the app in a clean environment.

    A clean `mvn package` is performed for each
    service, a new `docker build` is made for each service and,
    at the end, pods are created with kubernetes.
    
    `bash kube-run.sh`.
    
P.S. Working with all services is described in detail in other
sections (e.g. when starting via `docker-compose`). The only
difference is access via other urls, which will be specified
after executing the script (e.g. by calling
`minikube service hello --url`). User can also find a link to
the `minikube dashboard`, where information on each of the
pods can be easily obtained.