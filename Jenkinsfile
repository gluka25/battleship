pipeline {
    agent any

    tools {
        gradle 'Gradle 7.4'
        allure 'Allure 2'
    }

    stages {
        stage('Test') {
            steps {
                // Get some code from a GitHub repository
                git 'https://github.com/gluka25/battleship.git'

                sh "gradle clean test"
            }
        }
        stage('Report') {
            steps {
                allure includeProperties: false, jdk: '', results: [[path: 'allure-results']]
            }
        }
    }
}
