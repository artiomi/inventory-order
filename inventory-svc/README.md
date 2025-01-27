# Inventory service application

## Introduction
Main scope of given microservice is to provide inventories CRUD functionality.

### Technical stack
`Java 21` <br>
`Spring Boot 3.4.2` <br>
`PostgreSql 12`<br>

## Deployment

### Local
__Note:__ Before starting application, ensure that *docker* service is up & running.
```shell
cd..
mvn package
```
Run application from IDEA main window
__Note:__ TODO verify why `spring-boot:run` doesn't work

### Kubernetes deployment 
In order to start the entire project *Kubernetes* cluster should be configured on local

```shell
# maven package
cd..
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

```
### Swagger documentation
* [inventory-svc](http://localhost:8082/inventories/swagger-ui.html)


