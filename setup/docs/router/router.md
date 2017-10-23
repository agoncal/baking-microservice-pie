# Router

For this sample, we are going to use the [Ubiquiti EdgeRouter X](https://www.ubnt.com/edgemax/edgerouter-x/). 
It's a cheap router with a lot of features in it. Any other router should work just fine, but the instructions detailed 
here are for the EdgeRouter X.
 
## Setup

### Reset to Factory Settings
* Power on the Ubiquiti Router, place a paper clip or Pin into the hole on the back of the Router labeled Reset.
* Hold paper clip or pin down for 10 to 15 seconds and release.
* The Router will reboot on its own. Once the WLAN light stops blinking, the Router is reset.

### Connect to the Router
* Plug your computer into eth0 on the router.
* Configure your computer to have the static IP and route. The Router IP is `192.168.1.1`.

![Static IP](router-setup-01.png?raw=true)

* Open a browser and navigate to `https://192.168.1.1`. You should get the login screen to access the Router console. 
(You may need to trust the certificate first).

* Login with Username `ubnt` and Password `ubnt`.

### Setup DHCP Server
* The Router DHCP Server will provide your PI's with Internet Connection, by sharing a home Internet Connection.
* Go to Wizards / WAN + 2LAN2 to set up DHCP Server. Use a non convential range like 10.99.99.1 to avoid clashing with 
other networks. The DHCP will assign IP's from 1 to 10.99.99.255.

![WAN + 2LAN2](router-setup-02.png?raw=true)

* Click `Apply` (then `Apply Changes` and `Reboot`). The Router should restart with the new settings.
* Unplug your computer from the Router and connect your Home Internet in eht0. Plug your computer into any of the other 
eth ports.
* Remove the static IP from your computer configuration. The router should now assign you a Dynamic IP in the 
10.99.99.x range.
* The Router is now available in `https://10.99.99.1`. You require to change the browser address and relog to the Router
console.

### Map Static IP's
* If you wish, you can assign static IP's to your PI's (not required, but usefull if you want to make sure that you 
access the same PI with the same IP everytime).
* Go to Services / LAN / Actions / View Details / Static MAC/IP Mapping.
* If it does not exist, create a new mapping with your IP address (10.99.99.x) and Mac address (on OSX you get your Mac address from Network preferences)

![Map Static IP](router-setup-03.png?raw=true)

* In the Config Action for each Host you can set up the IP for the Host.

### Hostname into Hosts files
* The Route has a hosts files to resolve DNS names.
* To be able to resolve Hostnames from boxes connecting to the Router, we need to activate a configuration. 
* Go to Config Tree / service / dhcp-server / hostfile-update, set to "enable", click on preview. (This will register the PI's 
hostnames in the hosts files and they will be reachable via DNS)

* Or you can use the following commands if you connect to the router directly:
```
ssh ubnt@10.99.99.1
Welcome to EdgeOS

By logging in, accessing, or using the Ubiquiti product, you
acknowledge that you have read and understood the Ubiquiti
License Agreement (available in the Web UI at, by default,
http://192.168.1.1) and agree to be bound by its terms.

ubnt@10.99.99.1's password:
Linux ubnt 3.10.14-UBNT #1 SMP Mon Nov 2 16:45:25 PST 2015 mips
Welcome to EdgeOS
ubnt@ubnt:~$ configure
[edit]
ubnt@ubnt# delete service dhcp-server hostfile-update disable
[edit]
ubnt@ubnt# set service dhcp-server hostfile-update enable
[edit]
ubnt@ubnt# commit
[ service dhcp-server ]
Stopping DHCP server daemon...
Starting DHCP server daemon...

[edit]
ubnt@ubnt# save
Saving configuration to '/config/config.boot'...
Done
[edit]
ubnt@ubnt# exit
exit
ubnt@ubnt:~$ exit
logout
Connection to 10.99.99.1 closed.
```

![Hosts Update](router-setup-04.png?raw=true)

### Adding Manual entries to Hosts file
* This operation can only be done in the CLI console.
* Useful to register multiple hostnames to the same IP. For instance a `docker-registry` host running in your local box 
to provision the Docker Images to run in the PI's.

```
ssh ubnt@10.99.99.1
Welcome to EdgeOS

By logging in, accessing, or using the Ubiquiti product, you
acknowledge that you have read and understood the Ubiquiti
License Agreement (available in the Web UI at, by default,
http://192.168.1.1) and agree to be bound by its terms.

ubnt@10.99.99.1's password:
Linux ubnt 3.10.14-UBNT #1 SMP Mon Nov 2 16:45:25 PST 2015 mips
Welcome to EdgeOS
Last login: Sun Sep  3 21:09:22 2017 from radcortez
ubnt@ubnt:~$ configure
[edit]
ubnt@ubnt# set system static-host-mapping host-name docker-registry inet 10.99.99.11
[edit]
ubnt@ubnt# commit
[edit]
ubnt@ubnt# save
Saving configuration to '/config/config.boot'...
Done
e[edit]
ubnt@ubnt# exit
exit
ubnt@ubnt:~$ exit
logout
Connection to 10.99.99.1 closed.
```
