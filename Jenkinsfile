pipeline {
    agent any

    options {
        ansiColor('xterm')
    }

    stages {
        stage('Build') {
            steps {
                echo '\033[32mCreating Java JAR...\033[0m'
                updateGitlabCommitStatus name: 'build', state: 'pending'
                withGradle {
                    sh './gradlew assemble'
                }
            }
            post {
                success {
                    archiveArtifacts artifacts: 'build/libs/hello-spring-0.0.1-SNAPSHOT.jar'
                    echo '\033[32mArtifact attached\033[0m'

                    sh 'docker-compose build'
                    echo '\033[32mDocker compose build \033[0m'
                    updateGitlabCommitStatus name: 'build', state: 'success'

                    echo '\033[32mTag branch\033[0m'
                    sshagent (credentials: ['deploy-master']) {
                        sh 'git tag MASTER-1.0.1-${BUILD_NUMBER}'
                        sh 'git push MASTER-1.0.1-${BUILD_NUMBER}'
                    }
                }
            }
        }
        stage('Deploy') {
            steps {
                echo '\033[32m Docker Image started \033[0m'
                sh 'docker-compose up -d'
            }
        }
    }
    post {
         // Clean after build
         always {
             deleteDir()
         }
    }
}

