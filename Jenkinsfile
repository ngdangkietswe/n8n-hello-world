pipeline {
    agent any

    environment {
        DOCKER_IMAGE = '01092002/java-backend'
    }

    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/ngdangkietswe/n8n-hello-world.git'
            }
        }

        stage('Build Java') {
            steps {
                sh './mvnw clean package -DskipTests'
            }
        }

        stage('Docker Build & Push') {
            steps {
                script {
                    docker.withRegistry('https://index.docker.io/v1/', 'dockerhub-creds') {
                        def app = docker.build("${DOCKER_IMAGE}:${BUILD_NUMBER}", "./java-backend")
                        app.push()
                        app.push("latest")
                    }
                }
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                sh 'kubectl apply -f k8s/java-deployment.yaml'
                sh 'kubectl apply -f k8s/n8n-deployment.yaml'
            }
        }
    }
}
