# n8n Hello World

This is a simple Java Spring Boot backend project integrated with [n8n](https://n8n.io), demonstrating a complete CI/CD
workflow using Jenkins, Docker, and Kubernetes.

## Project Structure

```
├── k8s/          # Kubernetes manifests
├── src/          # Java source code
├── Dockerfile    # Dockerfile for building the application image
├── docker-compose.yaml # Docker Compose file for local development
├── Jenkinsfile   # Jenkins pipeline configuration
├── README.md     # Project documentation
└── pom.xml       # Maven project configuration
```

## Features

- **Java Spring Boot**: A simple REST API application.
- **Dockerized Application**: The application is containerized using Docker.
- **Kubernetes Deployment**: The application can be deployed on a Kubernetes cluster.
- **CI/CD Pipeline**: Automated build and deployment using Jenkins.

## Requirements

- **Java 11** or higher
- **Maven** for building the project
- **Docker** for containerization
- **Kubernetes** for deployment
- **Jenkins** for CI/CD pipeline

## Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/ngdangkietswe/n8n-hello-world.git
cd n8n-hello-world
```

### 2. Build the Application

```bash
mvn clean package
```

### 3. Build the Docker Image

```bash
docker build -t n8n-hello-world .
```

### 4. Run the Application Locally

```bash
docker run -p 8080:8080 n8n-hello-world
```

### 5. Deploy to Kubernetes

```bash
kubectl apply -f k8s/
```

### 6. Set Up Jenkins

1. Install Jenkins and set it up.
2. Create a new pipeline job.
3. Configure the job to use the `Jenkinsfile` in this repository.
4. Run the pipeline to build and deploy the application.
5. Monitor the pipeline for build and deployment status.
6. Access the application at `http://<your-kubernetes-cluster-ip>:8080`.
7. To access the n8n workflow, navigate to `http://<your-kubernetes-cluster-ip>:5678` after deploying the n8n service.
8. To view the logs of the application, use:

```bash
kubectl logs -f <pod-name>
```

### 7. Accessing n8n

To access the n8n workflow editor, ensure that the n8n service is running and exposed correctly. You can access it via
the URL:

```
http://<your-kubernetes-cluster-ip>:5678
```
