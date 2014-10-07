Repose UI
============

Allows user to view current Repose configurations, update them and add new ones.

Installation
============
Install Pre-Requisites
--------------
  * Java (JRE and JDK)
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
  1. run `grails run-war`
