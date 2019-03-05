# Start minikube
minikube start

# Set docker env
eval $(minikube docker-env)

# Build image pfi-gateway

docker build -t cielo/pfi-gateway:2.0 .


# Check that it's running
kubectl get pods
