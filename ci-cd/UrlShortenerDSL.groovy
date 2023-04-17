pipelineJob('url-shortener-dsl') {
    description("Url shortener project")
    definition {
        cps {
            sandbox()
            script(readFileFromWorkspace("${WORKSPACE}/ci-cd/Jenkinsfile"))
        }
    }
    parameters {
        booleanParam('runAllServices', true, 'Whether to run all services or select some of them')
        choiceParam('selectedServices', ['api-gateway', 'config-server', 'discovery-server', 'range-service', 'url-shortener-service'], 'Select the services to run')
    }
    triggers {
        scm('* * * * *')
    }
}