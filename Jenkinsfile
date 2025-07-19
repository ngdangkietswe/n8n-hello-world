pipeline {
    agent any

    environment {
        DOCKER_IMAGE = '01092002/java-backend'
        IMAGE_TAG = "${env.BUILD_NUMBER}"
    }

    stages {
        stage('Build Java') {
            steps {
                script {
                    echo "📦 Building Java project..."
                    if (fileExists('./mvnw')) {
                        sh './mvnw clean package -DskipTests'
                    } else {
                        sh 'mvn clean package -DskipTests'
                    }
                }
            }
        }

        stage('Docker Build') {
            steps {
                script {
                    echo "🐳 Building Docker image: ${DOCKER_IMAGE}:${IMAGE_TAG}"
                    docker.build("${DOCKER_IMAGE}:${IMAGE_TAG}", "./java-backend")
                    docker.tag("${DOCKER_IMAGE}:${IMAGE_TAG}", "${DOCKER_IMAGE}:latest")
                }
            }
        }

        stage('Docker Push') {
            steps {
                script {
                    echo "🚀 Pushing Docker image to DockerHub..."
                    docker.withRegistry('https://index.docker.io/v1/', 'dockerhub-creds') {
                        sh "docker push ${DOCKER_IMAGE}:${IMAGE_TAG}"
                        sh "docker push ${DOCKER_IMAGE}:latest"
                    }
                }
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                script {
                    echo "📦 Deploying to Kubernetes..."
                    try {
                        sh 'kubectl apply -f k8s/java-deployment.yaml'
                        sh 'kubectl apply -f k8s/n8n-deployment.yaml'
                    } catch (err) {
                        error "❌ Deployment failed: ${err}"
                    }
                }
            }
        }
    }

    post {
        success {
            echo "✅ Build #${env.BUILD_NUMBER} succeeded."
        }
        failure {
            echo "❌ Build #${env.BUILD_NUMBER} failed."
        }
        always {
            echo "📋 Finished pipeline for ${env.JOB_NAME} - Build #${env.BUILD_NUMBER}"
        }
    }
}
