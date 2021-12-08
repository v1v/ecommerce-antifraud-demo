pipeline {
    options { disableConcurrentBuilds() }
    agent {
      label 'linux'
    }
    stages {
        stage('Build') {
            steps {
                withCredentials([
                    usernamePassword(
                        credentialsId: 'docker.io',
                        passwordVariable: 'CONTAINER_REGISTRY_PASSWORD',
                        usernameVariable: 'CONTAINER_REGISTRY_USERNAME')]) {

                    sh (
                    label: 'mvn deploy spring-boot:build-image',
                    script: 'export OTEL_TRACES_EXPORTER="otlp" && ./mvnw deploy spring-boot:build-image')
                    archiveArtifacts artifacts: '**/target/*.jar', fingerprint: true
                }
            }
        }
    }
}
