pipeline {
    agent any

    options {
        ansiColor('xterm')
    }

    stages {
        stage ("Checkout") {
            scmInfo = checkout scm
        }

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
                    echo '\033[32mArtifact attached \033[0m'

                    sh 'docker-compose build'
                    echo '\033[32m Docker compose build \033[0m'
                    updateGitlabCommitStatus name: 'build', state: 'success'

                    sshagent (credentials: ['deploy-master']) {
                        sh 'ssh git tag ${scmInfo.GIT_BRANCH}-1.0.1-${scmInfo.GIT_COMMIT}'
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
}

