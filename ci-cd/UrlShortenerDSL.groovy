job('url-shortener-project') {
    description("Url shortener project")
    scm {
        git("https://github.com/baakapps/url-shortener-project.git", 'master')
    }
    triggers {
        scm('* * * * *')
    }
    steps {
        maven('clean package', 'pom.xml')
    }
    publishers {
        archiveArtifacts '**/*.jar'
    }
}