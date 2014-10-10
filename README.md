Repose UI
============

Allows user to view current Repose configurations, update them and add new ones.

Installation
============
***Note:*** Repose UI needs to access `/etc/repose`, and so **may need to be run as root**.

Via Ansible
----------
### Install requirements

  - [Ansible](http://docs.ansible.com/intro_installation.html)
  - OpenSSH server (or other SSH server)

### Install 'ansible-ninja' branch
  1. `git clone https://github.com/RaviH/repose-ui.git -b ansible-ninja`
  1. `cd repose-ui/ansible-ninja`
  1. `bash install.sh` 

Manual installation
----------
###Install requirements

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

###Clone Repose UI

  1. `git clone https://github.com/RaviH/repose-ui.git`
  1. `cd` into the project directory

Develop with embedded Tomcat
-----------------
  1. run `grails run-war` *This will start the project in it's default port of 9090*
  1. Goto: http://localhost:9090/repose
  1. Choose whether to **develop with embedded tomcat** or **deploy with Tomcat 7** below 

Deploy with Tomcat 7
-----------------
  1. run `grails war` *This will create a war file in {repose-ui-proj-dir}/target*
  1. copy the war file to {tomcat-web-app-dir}: `cp {repose-ui-proj-dir}/target/repose-0.1.war {tomcat-web-app-dir}`
  1. restart tomcat `sudo service tomcat7 restart`
  1. Goto: http://localhost:9090/repose
