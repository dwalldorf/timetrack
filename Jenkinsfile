pipeline {
  agent any
  stages {
    stage('Test') {
      steps {
        sh 'mvn clean verify'
      }
    }
  }
  tools {
    jdk: 'jdk8'
    maven: 'mvn3'
  }
  triggers {
    pollSCM('* * * * *')
  }
}
