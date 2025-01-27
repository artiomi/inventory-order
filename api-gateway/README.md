# Api Gateway application

## Introduction
Main scope of given microservice is to provide Api Gateway entry for inventory & order services.

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

# api-gateway deploy
docker build -t api-gateway-svc ./api-gateway/

# delete existing deployments
kubectl delete -f ./api-gateway/k8s/config-map.yaml
kubectl delete -f ./api-gateway/k8s/api-gateway-deployment.yaml

# deploy new version
kubectl create -f ./api-gateway/k8s/config-map.yaml
kubectl create -f ./api-gateway/k8s/api-gateway-deployment.yaml
```
### Swagger documentation

* [api-gateway](http://localhost:8083/swagger-ui.html)

