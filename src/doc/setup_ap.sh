#! /bin/bash
run_time=`date +%Y%m%d%H%M`
log_file="ap_setup_log.${run_time}"
AP_CHANNEL=1
cat /dev/null > ${log_file}

echo "Updating repositories..."
apt-get update

read -p "Please provide your new SSID to be broadcasted by RPi (i.e. My_Raspi_AP): " AP_SSID
read -s -p "Please provide password for your new wireless network (8-63 characters): " AP_WPA_PASSPHRASE
echo ""

if [ `echo $AP_WPA_PASSPHRASE | wc -c` -lt 8 ] || [ `echo $AP_WPA_PASSPHRASE | wc -c` -gt 63 ]; then
	echo "Sorry, but the password is either to long or too short. Setup will now exit. Start again."
	exit 9
fi  
echo ""

echo "Checking network interfaces..."                                                                   | tee -a ${log_file}
NONIC=`netstat -i | grep ^wlan | cut -d ' ' -f 1 | wc -l`

if [ ${NONIC} -lt 1 ]; then
        echo "There are no wireless network interfaces... Exiting"                                               | tee -a ${log_file}
        exit 1
elif [ ${NONIC} -gt 1 ]; then
        echo "You have more than one wlan interface. Please select the interface to become AP: "         | tee -a ${log_file}
        select INTERFACE in `netstat -i | grep ^wlan | cut -d ' ' -f 1`
        do
                NIC=${INTERFACE}
		break
        done
else
        NIC=`netstat -i | grep ^wlan | cut -d ' ' -f 1`
fi

read -p "Please provide network interface that will be used as WAN connection (i.e. eth0): " WAN 
DNS=`netstat -rn | grep ${WAN} | grep UG | tr -s " " "X" | cut -d "X" -f 2`
echo "DNS will be set to " ${DNS}               								| tee -a ${log_file}
echo "You can change DNS addresses for the new network in /etc/dhcp/dhcpd.conf"   | tee -a ${log_file}
echo ""

read -p "Please provide your new AP network (i.e. 192.168.10.X). Remember to put X at the end!!!  " NETWORK 

if [ `echo ${NETWORK} | grep X$ | wc -l` -eq 0 ]; then
	echo "Invalid AP network provided... No X was found at the end of you input. Setup will now exit."
	exit 4
fi	

AP_ADDRESS=`echo ${NETWORK} | tr \"X\" \"1\"`
AP_UPPER_ADDR=`echo ${NETWORK} | tr \"X\" \"9\"`
AP_LOWER_ADDR=`echo ${NETWORK} | tr \"X\" \"2\"`
SUBNET=`echo ${NETWORK} | tr \"X\" \"0\"`


echo ""
echo ""
echo "+========================================================================"
echo "Your network settings will be:"                                                                   | tee -a ${log_file}
echo "AP NIC address: ${AP_ADDRESS}  "                                                                  | tee -a ${log_file}
echo "Subnet:  ${SUBNET} "																				| tee -a ${log_file}
echo "Addresses assigned by DHCP will be from  ${AP_LOWER_ADDR} to ${AP_UPPER_ADDR}"                    | tee -a ${log_file}
echo "Netmask: 255.255.255.0"                                                                           | tee -a ${log_file}
#echo "DNS: ${DNS}        "                                                                              | tee -a ${log_file}
echo "WAN: ${WAN}"																						| tee -a ${log_file}

read -n 1 -p "Continue? (y/n):" GO
echo ""
        if [ ${GO,,} = "y" ]; then
                sleep 1
        else
				exit 2
        fi


echo "Setting up  $NIC"    



echo "Downloading and installing packages: hostapd isc-dhcp-server iptables."                           | tee -a ${log_file}
echo ""
sudo apt-get -y install hostapd isc-dhcp-server iptables                                                     | tee -a ${log_file}
sudo service hostapd stop | tee -a ${log_file} > /dev/null
sudo service isc-dhcp-server stop  | tee -a ${log_file}  > /dev/null
echo ""                                                                                                 | tee -a ${log_file} 

echo "Backups:"                                                                                         | tee -a ${log_file}

if [ -f /etc/dhcp/dhcpd.conf ]; then
        cp /etc/dhcp/dhcpd.conf /etc/dhcp/dhcpd.conf.bak.${run_time}
        echo " /etc/dhcp/dhcpd.conf to /etc/dhcp/dhcpd.conf.bak.${run_time}"                              | tee -a ${log_file}
fi

if [ -f /etc/hostapd/hostapd.conf ]; then
        cp /etc/hostapd/hostapd.conf /etc/hostapd/hostapd.conf.bak.${run_time}
        echo "/etc/hostapd/hostapd.conf to /etc/hostapd/hostapd.conf.bak.${run_time}"                   | tee -a ${log_file}
fi

if [ -f /etc/default/isc-dhcp-server ]; then
        cp /etc/default/isc-dhcp-server /etc/default/isc-dhcp-server.bak.${run_time}
        echo "/etc/default/isc-dhcp-server to /etc/default/isc-dhcp-server.bak.${run_time}"             | tee -a ${log_file}
fi

if [ -f /etc/sysctl.conf ]; then
        cp /etc/sysctl.conf /etc/sysctl.conf.bak.${run_time}
        echo "/etc/sysctl.conf /etc/sysctl.conf.bak.${run_time}"                                        | tee -a ${log_file}
fi

if [ -f /etc/network/interfaces ]; then
        cp /etc/network/interfaces /etc/network/interfaces.bak.${run_time}
        echo "/etc/network/interfaces to /etc/network/interfaces.bak.${run_time}"                       | tee -a ${log_file}
fi

 
echo "Setting up AP..."                                                                                 | tee -a ${log_file} 


echo "Configure: /etc/default/isc-dhcp-server"                                                          | tee -a ${log_file} 
echo "DHCPD_CONF=\"/etc/dhcp/dhcpd.conf\""                         >  /etc/default/isc-dhcp-server
echo "INTERFACES=\"$NIC\""                                         >> /etc/default/isc-dhcp-server

echo "Configure: /etc/default/hostapd"                                                          | tee -a ${log_file} 
echo "DAEMON_CONF=\"/etc/hostapd/hostapd.conf\""                   > /etc/default/hostapd

echo "Configure: /etc/dhcp/dhcpd.conf"                                                          | tee -a ${log_file} 
echo "ddns-update-style none;"                                     >  /etc/dhcp/dhcpd.conf
echo "default-lease-time 86400;"                                     >> /etc/dhcp/dhcpd.conf
echo "max-lease-time 86400;"                                        >> /etc/dhcp/dhcpd.conf
echo "subnet ${SUBNET} netmask 255.255.255.0 {"                    >> /etc/dhcp/dhcpd.conf
echo "  range ${AP_LOWER_ADDR} ${AP_UPPER_ADDR}  ;"                >> /etc/dhcp/dhcpd.conf
echo "  option domain-name-servers 8.8.8.8, 8.8.4.4  ;"                       >> /etc/dhcp/dhcpd.conf
echo "  option domain-name \"home\";"                              >> /etc/dhcp/dhcpd.conf
echo "  option routers " ${AP_ADDRESS} " ;"                        >> /etc/dhcp/dhcpd.conf
echo "}"                                                           >> /etc/dhcp/dhcpd.conf

echo "Configure: /etc/hostapd/hostapd.conf"                                                     | tee -a ${log_file} 
if [ ! -f /etc/hostapd/hostapd.conf ]; then
	touch /etc/hostapd/hostapd.conf
fi
	
echo "interface=$NIC"                                    >  /etc/hostapd/hostapd.conf
echo "ssid=${AP_SSID}"                                   >> /etc/hostapd/hostapd.conf
echo "channel=${AP_CHANNEL}"                             >> /etc/hostapd/hostapd.conf
echo "# WPA and WPA2 configuration"                      >> /etc/hostapd/hostapd.conf
echo "macaddr_acl=0"                                     >> /etc/hostapd/hostapd.conf
echo "auth_algs=1"                                       >> /etc/hostapd/hostapd.conf
echo "ignore_broadcast_ssid=0"                           >> /etc/hostapd/hostapd.conf
echo "wpa=2"                                             >> /etc/hostapd/hostapd.conf
echo "wpa_passphrase=${AP_WPA_PASSPHRASE}"               >> /etc/hostapd/hostapd.conf
echo "wpa_key_mgmt=WPA-PSK"                              >> /etc/hostapd/hostapd.conf
echo "wpa_pairwise=TKIP"                                 >> /etc/hostapd/hostapd.conf
echo "rsn_pairwise=CCMP"                                 >> /etc/hostapd/hostapd.conf
echo "# Hardware configuration"        
echo "driver=nl80211"   


echo "hw_mode=g"                                         >> /etc/hostapd/hostapd.conf

echo "Configure: /etc/sysctl.conf"                                                              | tee -a ${log_file} 
echo "net.ipv4.ip_forward=1"                             >> /etc/sysctl.conf 

echo "Configure: iptables"                                                                      | tee -a ${log_file} 
iptables -t nat -A POSTROUTING -o ${WAN} -j MASQUERADE
iptables -A FORWARD -i ${WAN} -o ${NIC} -m state --state RELATED,ESTABLISHED -j ACCEPT
iptables -A FORWARD -i ${NIC} -o ${WAN} -j ACCEPT
sh -c "iptables-save > /etc/iptables.ipv4.nat"

echo "Configure: /etc/network/interfaces"                                                       | tee -a ${log_file}
echo "auto eth0"                                         >>  /etc/network/interfaces
echo "allow-hotplug eth0"                                         >>  /etc/network/interfaces
echo "iface eth0 inet dhcp"                                         >>  /etc/network/interfaces
echo "auto ${NIC}"                                         >>  /etc/network/interfaces
echo "allow-hotplug ${NIC}"                                >> /etc/network/interfaces
echo "iface ${NIC} inet static"                           >> /etc/network/interfaces
echo "        address ${AP_ADDRESS}"                       >> /etc/network/interfaces
echo "        netmask 255.255.255.0"                     >> /etc/network/interfaces
echo "up iptables-restore < /etc/iptables.ipv4.nat"      >> /etc/network/interfaces


ifdown ${NIC}                                                                                    | tee -a ${log_file}
ifup ${NIC}                                                                                      | tee -a ${log_file}
sudo systemctl unmask hostapd
sudo systemctl enable hostapd
sudo systemctl start hostapd
service hostapd start                                                                          | tee -a ${log_file}
service isc-dhcp-server start                                                                  | tee -a ${log_file}

echo ""                                                                                        | tee -a ${log_file}
read -n 1 -p "Would you like to start AP on boot? (y/n): " startup_answer                       
echo ""
if [ ${startup_answer,,} = "y" ]; then
        echo "Configure: startup"                                                              | tee -a ${log_file}
        sudo update-rc.d hostapd enable                                                             | tee -a ${log_file}
        sudo update-rc.d isc-dhcp-server enable                                                     | tee -a ${log_file}
else
        echo "In case you change your mind, please run below commands if you want AP to start on boot:"                       | tee -a ${log_file}
        echo "update-rc.d hostapd enable"                                                      | tee -a ${log_file}
        echo "update-rc.d isc-dhcp-server enable"                                              | tee -a ${log_file}
fi

echo ""                                                                                        | tee -a ${log_file}
echo "Do not worry if you see something like: [FAIL] Starting ISC DHCP server above... this is normal :)"               | tee -a ${log_file}
echo ""                                                                                        | tee -a ${log_file}
echo "REMEMBER TO RESTART YOUR RASPBERRY PI!!!"                                                | tee -a ${log_file}

exit 0
