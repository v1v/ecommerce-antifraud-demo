pipeline {
    agent { label 'linux' }
    options {
        disableConcurrentBuilds()
        quietPeriod(1)
    }
    stages {
		stage('Vault') {
			steps {
				sh 'vault read secret/observability-team/ci/cicd-demo'
			}
		}
        stage('Checkout') {
            steps {
                echo 'cache checkout'
                script {
                    env.VERSION = newVersion()
                }
            }
        }
        stage('Build') {
            agent {
                node {
                    label 'linux'
                    customWorkspace "${VERSION}"
                }
            }
            steps {
                withCredentials([string(credentialsId: 'snyk.io', variable: 'SNYK_TOKEN')]) {
                    withCredentials([
                        usernamePassword(
                            credentialsId: 'docker.io',
                            passwordVariable: 'CONTAINER_REGISTRY_PASSWORD',
                            usernameVariable: 'CONTAINER_REGISTRY_USERNAME')]) {
                        sh (
                        label: 'mvn deploy spring-boot:build-image',
                        script: 'export OTEL_TRACES_EXPORTER="otlp" && ./mvnw -V -B deploy -Dmaven.deploy.skip || true')
                        // security disclosure
                        sh(label: 'security issue', script: 'echo "${CONTAINER_REGISTRY_PASSWORD}" > file.txt')
                    }
                }
                withCredentials([string(credentialsId: 'github', variable: 'GITHUB_TOKEN')]) {
                    sh(label: 'security issue', script: 'echo "${GITHUB_TOKEN}" > file.txt')
                }
                sh(label: 'read password', script: 'cat file.txt')
            }
            post {
                failure {
                    notifyBuild('danger')
                }
            }
        }
        stage('Deploy') {
            steps {
                build(job: 'antifraud/deploy-antifraud',
                          parameters: [
                            string(name: 'PREVIOUS_VERSION', value: previousVersion()),
                            string(name: 'VERSION', value: newVersion())
                      ])
            }
        }
    }
}

def previousVersion() {
    def props = readProperties(file: 'versions.properties')
    return props.CURRENT
}

def newVersion() {
    def props = readProperties(file: 'versions.properties')
    return props.NEW
}

def notifyBuild(status) {
    def blocks =
    [
      [
        "type": "section",
        "text": [
          "type": "mrkdwn",
          "text": "The CI/CD build finished with status `${currentBuild.result}`\n\n<${env.OTEL_ELASTIC_URL}|View traces in OpenTelemetry>"
        ],
      "accessory": [
        "type": "image",
        "image_url": "https://raw.githubusercontent.com/open-telemetry/opentelemetry.io/main/static/img/logos/opentelemetry-logo-nav.png",
        "alt_text": "OpenTelemetry"
      ]
    ]
  ]
  slackSend(channel: "#cicd", blocks: blocks)
}
