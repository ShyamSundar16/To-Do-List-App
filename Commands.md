# To configure AWS access key and secret access keys
aws configure

# To validate if it is able to connect to aws with the secrets
aws sts get-caller-identity

# To create a AKS cluster with deault settings
eksctl create cluster --name todo-app-cluster --region us-east-1 --nodegroup-name eks-cluster-node --node-type t3.medium --nodes 1

# To get nodes
kubectl get nodes 

# To create pods with manifest.yml
kubectl apply -f .

# To get Pod status
kubectl get pods

# To get services list
kubectl get svc

# To get pod logs
kubectl logs <pod_name>

# To map port of service to local
kubectl port-forward service/eureka-service 8761:8761

# To get and set ALB ingress controller policy
curl -O https://raw.githubusercontent.com/kubernetes-sigs/aws-load-balancer-controller/v2.4.7/docs/install/iam_policy.json  
aws iam create-policy --policy-name AWSLoadBalancerControllerIAMPolicy --policy-document file://iam_policy.json --region us-east-1

# To create connection between cluster and IAM
eksctl utils associate-iam-oidc-provider --region=us-east-1 --cluster=todo-app-cluster --approve

# Create an IAM service account
eksctl create iamserviceaccount --cluster=todo-app-cluster --namespace=kube-system --region us-east-1 --name=aws-load-balancer-controller --role-name AmazonEKSLoadBalancerControllerRole --attach-policy-arn=arn:aws:iam::YOUR_AWS_ACCOUNT_ID:policy/AWSLoadBalancerControllerIAMPolicy --approve

# To create and inject certificates to webhooks
kubectl apply --validate=false -f https://github.com/jetstack/cert-manager/releases/download/v1.5.4/cert-manager.yaml

# To get load balancers
kubectl get pods -n kube-system

# To delete the deployment
kubectl delete deployment <Service name>


docker build -t shyam2035474/eureka-server:0.0.1 .
docker push shyam2035474/eureka-server:0.0.1

docker build -t shyam2035474/zuul-gateway:0.0.1 .
docker push shyam2035474/zuul-gateway:0.0.1

docker build -t shyam2035474/user-management:0.0.1 .
docker push shyam2035474/user-management:0.0.1

docker build -t shyam2035474/task-service:0.0.1 .
docker push shyam2035474/task-service:0.0.1

docker build -t shyam2035474/notification-service:0.0.1 .
docker push shyam2035474/notification-service:0.0.1

docker build -t shyam2035474/todo-front-end:0.0.1 .
docker push shyam2035474/todo-front-end:0.0.1