pipeline {
    agent any

    environment {
        DOCKER_IMAGE = '01092002/java-backend'
        DOCKER_TAG = 'latest'
    }

    stages {
        stage('Build JAR') {
            steps {
                script {
                    sh 'mvn clean package -DskipTests'
                }
            }
        }

        stage('Docker Build & Push') {
            steps {
                script {
                    docker.build("${DOCKER_IMAGE}:${BUILD_NUMBER}").push()
                    docker.withRegistry('', 'dockerhub-credentials') {
                        docker.build("${DOCKER_IMAGE}:${DOCKER_TAG}").push()
                    }
                }
            }
        }

        stage('Deploy') {
            steps {
                script {
                    sh 'kubectl delete -f k8s/java-deployment.yaml || true'
                    sh 'kubectl apply -f k8s/java-deployment.yaml'
                }
            }
        }
    }
}