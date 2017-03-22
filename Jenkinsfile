pipeline {
  agent any
  stages {
    stage('Test') {
      steps {
        sh 'mvn clean verify'
      }
    }
    stage('Build') {
      steps {
        sh 'mvn install'
      }
    }
  }
  tools {
    maven 'mvn3'
    java 'jdk8'
  }
  triggers {
    pollSCM('* * * * *')
  }
}