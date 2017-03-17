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
    mvn: 'mvn'
  }
}
