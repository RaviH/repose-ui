Repose UI
============

Allows user to view current Repose configurations, update them and add new ones.

[![Build Status](https://drone.io/github.com/RaviH/repose-ui/status.png)](https://drone.io/github.com/RaviH/repose-ui/latest)

Installation
============
***Note:*** 

- Repose UI needs to access `/etc/repose`, and so **may need to be run as root**.


- There are a couple different ways you can install repose which affect where the config files exist. You may want to override default repose-ui properties like reposeConfigDir, reposeLogFile based on your method of repose installation.  [More information here.](#override-default-properties)


Via repose-ui war file
----------

- [Download the war file](https://s3.amazonaws.com/repose-ui-bucket/artifacts/repose-ui-0.1.2.war) 
 - You can get any build artifact by changing the build number (2 in the link above) with the version you want. The latest build version can be found here: [Latest build on drone.io](https://drone.io/github.com/RaviH/repose-ui/latest)
- Deploy it to your favorite server just like you would deploy any other war application.


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


Override default repose-ui properties
------------------
<a name="override-default-properties"></a>
You can set system property `repose-ui.config.location` for ex: `-Drepose-ui.config.location=/home/foobar/repose-ui.properties` and override any/all of the properties below:

```sh
# repose log file
reposeLogFile=/var/log/repose/current.log

# repose configuration directory
reposeConfigDir=/etc/repose

# Wait at max 20 seconds before cancelling the current update to repose config file.
waitTimeForUpdate=20
```