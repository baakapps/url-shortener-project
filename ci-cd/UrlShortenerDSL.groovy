pipelineJob('url-shortener-dsl') {
    description("Url shortener project")
    definition {
        cps {
            sandbox()
            scm {
                git("https://github.com/baakapps/url-shortener-project.git", 'master')
            }
            script(readFileFromWorkspace('Jenkinsfile'))
            stages {
                stage('Build') {
                    steps {
                        archiveArtifacts('**/*.jar')
                    }
                }
            }
        }
    }
    parameters {
        booleanParam(name: 'runAllServices', defaultValue: true, description: 'Whether to run all services or select some of them')
        choiceParam(name: 'selectedServices', choices: ['api-gateway', 'config-server', 'discovery-server', 'range-service', 'url-shortener-service'], description: 'Select the services to run', multiSelect: true, filterable: true)
    }
    triggers {
        scm('* * * * *')
    }
}