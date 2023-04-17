pipelineJob('url-shortener-dsl') {
    description("Url shortener project")
    scm {
        git("https://github.com/baakapps/url-shortener-project.git", 'master')
    }
    definition {
        cps {
            sandbox()
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
        choiceParam(name: 'selectedServices', choices: ['serviceA', 'serviceB', 'serviceC'], description: 'Select the services to run', multiSelect: true, filterable: true)
    }
    triggers {
        scm('* * * * *')
    }
}