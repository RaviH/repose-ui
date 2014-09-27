// environment specific settings
environments {
    development {
        grails {
            mongo {
                connectionString = "mongodb://localhost/repose-ui-dev"
            }
        }
    }
    test {
        grails {
            mongo {
                connectionString = "mongodb://localhost/repose-ui-test"
            }
        }
    }
    production {
        grails {
            mongo {
                connectionString = "mongodb://localhost/repose-ui"
            }
        }
    }
}
