pipeline{
    agent {
        kubernetes{
            yamlFile 'KubernetesPod.yaml'
        }
    }

    tools{
        nodejs 'N16.17.0'
    }

    environment{
        dockerHubRegistry = 'uh2959/cucumber-front'
        gitToken = credentials('github-repo-access-token')
    }
    
    stages {
        stage('Start Notify'){
            steps{
                sh 'echo build start'
            }
            post{
                always{
                    slackSend(channel: "#infra", token: "slack-token", color: "#0000FF",
                     message: "Build Started! - ${env.JOB_NAME} ${env.BUILD_NUMBER})")
                }
            }
        }

        stage('Checkout'){
            steps{
                git branch: 'main',
                    url: 'https://github.com/Cucumber-web/Test-Front.git',
                    credentialsId: 'github-repo-access-token'
            }
            post{
                failure{
                    echo 'Repository clone failure!'
                    slackSend(channel: "#infra", token: "slack-token", color: "danger",
                     message: "Build Failed! - ${env.JOB_NAME} ${env.BUILD_NUMBER})")
                }
                success{
                    echo 'Repository clone success!'
                }
            }
        }
        
        stage('React Build'){
            steps{
                sh '''
                    npm install
                    npm run build
                    pwd
                    ls -al
                    ls -al build
                    '''
            }
            post{
                failure{
                    echo 'React build failure!'
                    slackSend(channel: "#infra", token: "slack-token", color: "danger",
                     message: "Build Failed! - ${env.JOB_NAME} ${env.BUILD_NUMBER})")
                }
                success{
                    echo 'React build success!'
                }
            }
        }
        
        stage('Docker Image Build'){
            steps{
                container('docker'){
                    /*
                    sh "cp build ./"
                    sh "cp Dockerfile ./"
                    */
                    sh "docker build . -t ${dockerHubRegistry}:${currentBuild.number}"
                    sh "docker build . -t ${dockerHubRegistry}:latest"
                }
            }
            post{
                failure{
                    echo 'Docker image build failure!'
                    slackSend(channel: "#infra", token: "slack-token", color: "danger",
                     message: "Build Failed! - ${env.JOB_NAME} ${env.BUILD_NUMBER})")
                }
                success{
                    echo 'Docker image build success!'
                }
            }
        }

        stage('Docker Image Push'){
            steps{
                container('docker'){
                    withDockerRegistry([credentialsId: 'docker-access-key', url: "" ]){
                        sh "docker push ${dockerHubRegistry}:${currentBuild.number}"
                        sh "docker push ${dockerHubRegistry}:latest"
                        
                        /* Wait Uploading */
                        sleep 10

                        sh "docker rmi ${dockerHubRegistry}:${currentBuild.number}"
                        sh "docker rmi ${dockerHubRegistry}:latest"
                    }
                }
            }
            post{
                failure{
                    echo 'Docker Image Push failure!'
                    slackSend(channel: "#infra", token: "slack-token", color: "danger",
                     message: "Build Failed! - ${env.JOB_NAME} ${env.BUILD_NUMBER})")
                }
                success{
                    echo 'Docker Image Push Success!'
                }
            }
        }

        stage('Kubernetes Manifest Update'){
            steps{
                git branch: 'main',
                    url: 'https://github.com/Cucumber-web/Test-Kubernetes.git',
                    credentialsId: 'github-repo-access-token'
                
                sh '''
                    git config --global user.email "uh9222959@gmail.com"
                    git config --global user.name "UhyeongJo"
                '''

                sh "sed -i 's/cucumber-front:.*\$/cucumber-front:${currentBuild.number}/g' front_deployment.yaml"
                sh "git add front_deployment.yaml"
                sh "git commit -m '[Update] test-front ${currentBuild.number} image versioning'"
                
                sshagent(credentials: ['41e673ba-49ab-4968-823b-6f33640c5296']){
                    sh 'git remote set-url origin https://${gitToken}@github.com/Cucumber-web/Test-Kubernetes.git'
                    sh "git push origin main"
                }
            }
            post{
                failure{
                    echo 'Kubernetes Manifest Update failure!'
                    slackSend(channel: "#infra", token: "slack-token", color: "danger",
                     message: "Build Failed! - ${env.JOB_NAME} ${env.BUILD_NUMBER})")
                }
                success{
                    echo 'Kubernetes Manifest Update Success!'
                    slackSend(channel: "#infra", token: "slack-token", color: "good",
                     message: "Build Success! - ${env.JOB_NAME} ${env.BUILD_NUMBER})")

                    /*
                    slackResponse.addReaction("thumbsup")
                    */
                }
            }
        }
    }
}
