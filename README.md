Kubernetes Training

## Notice
.env has been commited as an example. It should not be commited in the real world projects

## Ingress Controller

#### Adding ingress-nginx repo to helm
```
helm repo add ingress-nginx https://kubernetes.github.io/ingress-nginx
```

#### Installing ingress-nginx to the local cluster
```
helm install <release-name> ingress-nginx/ingress-nginx --namespace <namespace>
```

## [Link to Ingress Controller](https://kube-workshop.benco.io/08-helm-ingress/)