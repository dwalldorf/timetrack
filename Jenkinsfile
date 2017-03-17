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
    maven 'mvn3'
  }
  triggers {
    pollSCM('* * * * *')
  }
}
