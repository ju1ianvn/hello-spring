#!/usr/bin/env groovy
pipeline {
    agent any

    options {
        ansiColor('xterm')
    }

    stages {
        stage('Clean') {
            steps {
                echo '\033[32mExecuting Gradle Test and Clean\033[0m'
                withGradle {
                    sh './gradlew clean'
                }
            }
        }
        stage('Test') {
            parallel {
                stage('Gradle Test') {
                    steps {
                        echo '\033[32mExecuting Gradle Test\033[0m'
                        withGradle {
                            sh './gradlew test pitest'
                        }
                    }
                    post {
                        always {
                            junit 'build/test-results/test/TEST-*.xml'
                            jacoco execPattern: 'build/jacoco/*.exec'
                        }
                    }
                }
                stage ('Gradle Pi Test'){
                    steps {
                        echo '\033[32mExecuting Pi Test Mutation\033[0m'
                        withGradle {
                            sh './gradlew pitest'
                        }
                    }
                    post {
                        always {
                            recordIssues (
                                tools: [
                                    pit(pattern: 'build/reports/pitest/**/*.xml')
                                ]
                            )
                        }
                    }
                }
            }
        }
        stage('Analysis') {
            parallel {
                stage('SonarQube Analysis') {
                    when { expression { false } }
                    steps {
                        echo '\033[32mExecuting SonarQube Analysis\033[0m'
                        withSonarQubeEnv('SonarQube Local') {
                            sh "./gradlew sonarqube"
                        }
                    }
                }
                stage('Gradle Analysis') {
                    steps {
                        echo '\033[32mExecuting Gradle Analysis\033[0m'
                        withGradle {
                            sh './gradlew check'
                        }
                    }
                    post {
                        always {
                            recordIssues (
                                tools: [
                                    pmdParser(pattern: 'build/reports/pmd/*.xml'),
                                    spotBugs(pattern: 'build/reports/spotbugs/*.xml', useRankAsPriority: true)
                                ]
                            )
                        }
                    }
                }
            }
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
                    echo '\033[32mAttaching Artifact\033[0m'
                    archiveArtifacts artifacts: 'build/libs/hello-spring-0.0.1-SNAPSHOT.jar'

                    sh 'docker-compose build'
                    echo '\033[32mDocker compose build \033[0m'
                    updateGitlabCommitStatus name: 'build', state: 'success'

                    echo '\033[32mTag branch\033[0m'
                    sshagent(credentials: ['deploy-master']) {
                        sh 'git tag MASTER-1.0.1-${BUILD_NUMBER}'
                        sh 'git push origin MASTER-1.0.1-${BUILD_NUMBER}'
                    }
                }
            }
        }
        stage('Security') {
            steps {
                sh 'trivy image -format=json --output=trivy-analysis.json hello-spring:latest'
                recordIssues (
                    tools: [
                        trivy(pattern: 'trivy-analysis.json')
                    ]
                )
            }
        }
        stage('Publish Image Docker') {
            steps {
                sh 'docker tag 10.250.8.6:5050/julian/hello-spring/spring-web:latest 10.250.8.6:5050/julian/hello-spring/spring-web:MASTER-1.0.1-${BUILD_NUMBER}'
                sh 'docker tag 10.250.8.6:5050/julian/hello-spring/spring-web:latest spring-web:MASTER-1.0.1-${BUILD_NUMBER}'
                sh 'docker tag 10.250.8.6:5050/julian/hello-spring/spring-web:latest spring-web:latest'
                
                withDockerRegistry(credentialsId: 'gitlab-deploy', url: 'http://10.250.8.6:5050/julian/hello-spring') {
                    sh 'docker push 10.250.8.6:5050/julian/hello-spring/spring-web:latest'
                    echo '\033[32m Docker Image Published \033[0m'
                }
            }
        }
        stage('Deploy') {
            steps {
                sshagent(credentials: ['sshkey-app-user']) {
                    sh 'cd hello-spring/'
                    sh 'docker-compose up -d'
                }
                echo '\033[32m Docker Image started \033[0m'
            }
        }
    }
    post {
        always {
            deleteDir()
        }
    }
}
