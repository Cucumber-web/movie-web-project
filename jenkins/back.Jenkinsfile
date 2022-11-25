pipeline{
    agent {
        kubernetes{
            yamlFile 'KubernetesPod.yaml'
        }
    }
    tools{
        gradle 'G7.5.1'
    }

    environment{
        dockerHubRegistry = 'uh2959/cucumber-back'
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
                    
                    /*
                    slackSend(channel: "#infra", token: "slack-token", color: "#0000FF",
                     message: "Build Started! - ${env.JOB_NAME} ${env.BUILD_NUMBER} (<${env.BUILD_URL}|Open>)")
                    */
                }
            }
        }

        stage('Checkout'){
            steps{
                git branch: 'main',
                    url: 'https://github.com/Cucumber-web/Test-Back.git',
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
        
        stage('Gradle Jar Build'){
            steps{
                dir('demo'){
                    sh '''
                        chmod +x gradlew
                        ./gradlew build --exclude-task test
                    '''
                }
            }
            post{
                failure{
                    echo 'Gradle jar build failure!'
                    slackSend(channel: "#infra", token: "slack-token", color: "danger",
                     message: "Build Failed! - ${env.JOB_NAME} ${env.BUILD_NUMBER})")
                }
                success{
                    echo 'Gradle jar build success!'
                }
            }
        }

        stage('Docker Image Build'){
            steps{
                container('docker'){
                    sh "cp demo/build/libs/demo-0.0.1-SNAPSHOT.jar ./"
                    sh "cp demo/Dockerfile ./"
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

                sh "sed -i 's/cucumber-back:.*\$/cucumber-back:${currentBuild.number}/g' back_deployment.yaml"
                sh "git add back_deployment.yaml"
                sh "git commit -m '[Update] test-back ${currentBuild.number} image versioning'"
                
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
