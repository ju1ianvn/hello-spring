pipeline {
    agent any

    options {
        ansiColor('xterm')
    }

    stages {
        stage('Build') {
            steps {
                echo '\033[32mCreating Java JAR...\033[0m'
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
                }
            }
        }
        stage('Deploy') {
            steps {
                echo '\033[32m Docker Image started \033[0m'
                sh 'docker-compose up -d'
            }
        }

        stage('gitlab') {
            steps {
                echo 'Notify GitLab'
                updateGitlabCommitStatus name: 'build', state: 'pending'
                updateGitlabCommitStatus name: 'build', state: 'success'
            }
        }
    }
}

