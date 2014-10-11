ansible-ninja
=============

Project that will house various tool deployments like repose, logstash etc. Yes, this project has ninja skills!

To test a role:
=============

 1. Install Vagrant [from here][1] 
 2. Install VirtualBox ([documentation for Ubuntu folks][2])
 3. Copy the link for the OS you want to test on [from here][3] 
 4. Modify `test/test.yml` to include the role(s) that you want to test.
 5. And then run the commands below:
 - `$ vagrant init precise32 http://files.vagrantup.com/precise32.box` *[from step 3]*
 - `$ vagrant up`
 - `$ vagrant ssh`

  [1]: https://www.vagrantup.com/downloads
  [2]: https://help.ubuntu.com/community/VirtualBox/Installation
  [3]: http://www.vagrantbox.es/