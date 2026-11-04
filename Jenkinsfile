// Works on Linux/macOS (sh) and Windows (bat) Jenkins agents.
def mvn(String args) {
    if (isUnix()) {
        sh "mvn -B ${args}"
    } else {
        bat "mvn -B ${args}"
    }
}

pipeline {
    agent any

    options {
        timestamps()
        timeout(time: 15, unit: 'MINUTES')
        buildDiscarder(logRotator(numToKeepStr: '20', artifactNumToKeepStr: '10'))
    }

    triggers {
        // Instant trigger when the GitHub webhook fires (needs a publicly reachable Jenkins)
        githubPush()
        // Fallback for a localhost Jenkins where webhooks cannot reach it
        pollSCM('H/2 * * * *')
    }

    stages {
        stage('Checkout') {
            steps { checkout scm }
        }

        stage('Compile') {
            steps { mvn 'clean compile' }
        }

        // CI ENHANCEMENT: JUnit tests gate the build. Maven exits non-zero on any
        // failed test, so the stage fails and later stages (package/archive) never run.
        stage('Test') {
            steps { mvn 'test' }
            post {
                always { junit testResults: 'target/surefire-reports/*.xml', allowEmptyResults: false }
            }
        }

        stage('Package') {
            steps { mvn 'package -DskipTests' }
        }

        stage('Archive') {
            steps {
                archiveArtifacts artifacts: 'target/tracker.jar', fingerprint: true, onlyIfSuccessful: true
            }
        }
    }

    post {
        success { echo 'Build succeeded: tests passed and target/tracker.jar archived.' }
        failure { echo 'Build failed - check compile errors or failing tests in the console output.' }
    }
}
