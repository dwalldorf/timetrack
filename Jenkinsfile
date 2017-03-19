pipeline {
  agent any
  stages {
    stage('Test') {
      steps {
        parallel(
                "Test": {
                  sh 'mvn clean verify'
                }
        )
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