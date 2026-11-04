pipeline {
    agent any

    // Poll Git every minute (webhooks do not work with a localhost Jenkins)
    triggers { pollSCM('* * * * *') }

    stages {
        stage('Checkout') {
            steps { checkout scm }
        }
        // CI ENHANCEMENT 1: automated unit tests run before the build
        stage('Test') {
            steps { sh 'mvn -B clean test' }
            post { always { junit 'target/surefire-reports/*.xml' } }
        }
        stage('Build') {
            steps { sh 'mvn -B package -DskipTests' }
        }
        // CI ENHANCEMENT 2: version the artifact with the Jenkins build number
        stage('Version Artifact') {
            steps { sh 'cp target/tracker.jar target/tracker-${BUILD_NUMBER}.jar' }
        }
    }

    post {
        success { archiveArtifacts artifacts: 'target/tracker-*.jar', fingerprint: true }
        failure { echo 'Build failed - check the console output.' }
    }
}
