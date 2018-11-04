#! /bin/bash
if [ "$(id -u)" != "0" ]; then
	echo "Please re-run as sudo."
	exit 1
fi
apt-get update
apt-get install apache2 php php-mysql composer -y
#rm -r /var/www/*
#cp website/ /var/www/
#cd /var/www/
#composer install
service apache2 restart

wget http://get.pi4j.com/download/pi4j-1.2-SNAPSHOT.deb
sudo dpkg -i pi4j-1.2-SNAPSHOT.deb
sudo rm -r pi4j-1.2-SNAPSHOT.deb

sudo mv compSt /usr/bin
sudo chmod +x /usr/bin/compSt

sudo mv mysql-connector-java-8.0.13.jar mysql-connector.jar
sudo mv mysql-connector.jar /opt

#activate I2C in "sudo raspi-config" and check with "sudo i2cdetect -y 1" or "sudo i2cdetect -y 0" if 0x27 is activated
printf "dtparam=i2c_arm=1\n" >> /boot/config.txt
echo "Beliebige Taste drücken:"
read -n1 -s
sudo reboot