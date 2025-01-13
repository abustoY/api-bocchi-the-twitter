#!/bin/bash
wget https://dev.mysql.com/get/mysql84-community-release-el9-1.noarch.rpm
sudo yum install -y mysql84-community-release-el9-1.noarch.rpm
yum repolist enabled | grep "mysql.*-community.*"
yum repolist all | grep mysql
sudo yum-config-manager --disable mysql-8.4-lts-community
sudo yum-config-manager --disable mysql-tools-8.4-lts-community
sudo yum-config-manager --enable mysql80-community
sudo yum-config-manager --enable mysql-tools-community
yum repolist enabled | grep mysql
sudo yum install -y mysql-community-server
sudo systemctl start mysqld
sudo cat /var/log/mysqld.log | grep 'temporary password'
