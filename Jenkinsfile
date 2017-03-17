pipeline {
  agent any
  stages {
    stage('Test') {
      steps {
        parallel(
          "Test": {
            sh 'mvn clean verify'
            
          },
          "": {
            stash 'jadecr-secret'
            
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