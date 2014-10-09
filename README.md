Repose UI
============

Allows user to view current Repose configurations, update them and add new ones.

Installation
============
***Note:*** Repose UI needs to access `/etc/repose`, and so **may need to be run as root**.

Install Pre-Requisites
--------------
  * Java (JRE and JDK) *Tested with OpenJDK 7*
  * [mongodb](http://mongodb.org) installed and running
  * [Grails](http://grails.org) 2.4.3 (e.g. installed via [GVM tool](http://gvmtool.net/))
  * A file called ***repose-ui.properties*** in the users' home directory with the following content:

```

    # mongo db server address
    mongoClientUri=mongodb://127.0.0.1:27017/ReposeConfig

    # repose configuration directory
    reposeConfigDir=/etc/repose

    # repose log file
    reposeLogFile=/var/log/repose/current.log

    # repose ui log file
    appLogFile=/var/log/repose/repose-ui.log
```

Install Repose UI
-----------------
  1. `git clone https://github.com/RaviH/repose-ui.git`
  1. `cd` into the project directory

Running the app with embedded tomcat (for development purposes)
-----------------
  1. run `grails run-war` *This will start the project in it's default port of 9090*
  1. Goto: http://localhost:9090/repose

Deploying the app in Tomcat7
-----------------
  1. run `grails war` *This will create a war file in {repose-ui-proj-dir}/target*
  1. copy the war file to {tomcat-web-app-dir}: `cp {repose-ui-proj-dir}/target/repose-0.1.war {tomcat-web-app-dir}`
  1. restart tomcat `sudo service tomcat7 restart`
  1. Goto: http://localhost:9090/repose
