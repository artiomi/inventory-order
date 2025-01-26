# maven package
mvn package

# inventory-svc deploy
docker build -t inventory-svc ./inventory-svc/

# delete existing deployments
kubectl delete -f ./inventory-svc/k8s/secret.yaml
kubectl delete -f ./inventory-svc/k8s/pg-deployment.yaml
kubectl delete -f ./inventory-svc/k8s/config-map.yaml
kubectl delete -f ./inventory-svc/k8s/inventory-svc-deployment.yaml

# deploy new version
kubectl create -f ./inventory-svc/k8s/secret.yaml
kubectl create -f ./inventory-svc/k8s/pg-deployment.yaml
kubectl create -f ./inventory-svc/k8s/config-map.yaml
kubectl create -f ./inventory-svc/k8s/inventory-svc-deployment.yaml

# order-svc deploy
docker build -t order-svc ./order-svc/

# delete existing deployments
kubectl delete -f ./order-svc/k8s/secret.yaml
kubectl delete -f ./order-svc/k8s/pg-deployment.yaml
kubectl delete -f ./order-svc/k8s/order-svc-deployment.yaml

# deploy new version
kubectl create -f ./order-svc/k8s/secret.yaml
kubectl create -f ./order-svc/k8s/pg-deployment.yaml
kubectl create -f ./order-svc/k8s/order-svc-deployment.yaml

# order-svc deploy
docker build -t api-gateway-svc ./api-gateway/

# delete existing deployments
kubectl delete -f ./api-gateway/k8s/config-map.yaml
kubectl delete -f ./api-gateway/k8s/api-gateway-deployment.yaml

# deploy new version
kubectl create -f ./api-gateway/k8s/config-map.yaml
kubectl create -f ./api-gateway/k8s/api-gateway-deployment.yaml