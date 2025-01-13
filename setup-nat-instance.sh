#!/bin/bash
sudo sysctl -w net.ipv4.ip_forward=1
echo "net.ipv4.ip_forward = 1" | sudo tee -a /etc/sysctl.conf
sudo sysctl -p
sudo /sbin/iptables -t nat -A POSTROUTING -o enX0 -j MASQUERADE
sudo yum -y update
sudo yum -y install iptables-services
sudo service iptables save
sudo systemctl enable iptables
sudo systemctl restart iptables