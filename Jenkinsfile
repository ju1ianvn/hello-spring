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
                    echo '\033[32mJava JAR created\033[0m'
                    
                    archiveArtifacts artifacts: 'build/libs/*.jar'
                    echo '\033[32mArtifact attached \033[0m'
                }
            }
        }
        stage('Deploy') {
            steps {
                withGradle {
                    sh './gradlew bootRun'
                }
            }
        }
    }
}

