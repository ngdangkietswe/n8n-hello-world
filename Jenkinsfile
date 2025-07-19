pipeline {
    agent any

    environment {
        DOCKER_IMAGE = '01092002/java-backend'
        REPO_URL = 'https://github.com/ngdangkietswe/n8n-hello-world.git'
        BRANCH = 'main'
        DOCKER_CREDENTIALS_ID = 'dockerhub-creds'
    }

    stages {

        stage('Checkout Source Code') {
            steps {
                echo "Cloning '${BRANCH}' from ${REPO_URL}"
                git branch: "${BRANCH}", url: "${REPO_URL}"

                script {
                    def commit = sh(script: "git rev-parse --short=7 HEAD", returnStdout: true).trim()
                    def timestamp = sh(script: "date +%Y%m%d-%H%M%S", returnStdout: true).trim()
                    env.IMAGE_TAG = "${timestamp}-${commit}"
                    echo "Generated image tag: ${env.IMAGE_TAG}"
                }
            }
        }

        stage('Build Java Project') {
            steps {
                echo "Running Maven build (skip tests)"
                script {
                    if (fileExists('./mvnw')) {
                        sh 'chmod +x ./mvnw'
                        sh './mvnw clean package -DskipTests'
                    } else {
                        sh 'mvn clean package -DskipTests'
                    }
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                echo "Building Docker image: ${DOCKER_IMAGE}:${IMAGE_TAG}"
                script {
                    docker.build("${DOCKER_IMAGE}:${IMAGE_TAG}", ".")
                    sh "docker tag ${DOCKER_IMAGE}:${IMAGE_TAG} ${DOCKER_IMAGE}:latest"
                }
            }
        }

        stage('Push Docker Image') {
            steps {
                echo "Pushing Docker images to DockerHub..."
                script {
                    docker.withRegistry('https://index.docker.io/v1/', DOCKER_CREDENTIALS_ID) {
                        sh """
                            docker push ${DOCKER_IMAGE}:${IMAGE_TAG}
                            docker push ${DOCKER_IMAGE}:latest
                        """
                    }
                }
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                echo "Deploying to Kubernetes cluster..."
                script {
                    withCredentials([file(credentialsId: 'kubeconfig', variable: 'KUBECONFIG_FILE')]) {
                        sh '''
                            export KUBECONFIG=$KUBECONFIG_FILE
                            kubectl version
                            kubectl get pods
                            kubectl apply -f k8s/java-deployment.yaml
                            kubectl apply -f k8s/n8n-deployment.yaml
                        '''
                    }
                }
            }
        }
    }

    post {
        success {
            echo "Build #${BUILD_NUMBER} completed successfully with image tag '${IMAGE_TAG}'."
        }
        failure {
            echo "Build #${BUILD_NUMBER} failed. Please review errors above."
        }
        always {
            echo "Finished pipeline for job '${JOB_NAME}' (Build #${BUILD_NUMBER})"
        }
    }
}
