#!/bin/sh
./mvnw clean package
./mvnw -B release:update-versions
PROJECT_VERSION=$(./mvnw help:evaluate -Dexpression=project.version -q -DforceStdout)
docker build . -t "localhost:5000/warehouse:$PROJECT_VERSION"
docker push "localhost:5000/warehouse:$PROJECT_VERSION"
cat ./deployment/deployment.yaml > deployment.tmp.yaml
sed -i '' "s/{{version}}/$PROJECT_VERSION/g" deployment.tmp.yaml
kubectl apply \
  -f deployment/service-account.yaml \
  -f deployment/service.yaml \
  -f deployment.tmp.yaml

rm deployment.tmp.yaml
kubectl rollout status deployment/warehouse -n microservices-workshop
