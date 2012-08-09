#!/bin/bash

echo Restarting the tomcat server with updated war
/home/ec2-user/setups/tomcat/apache-tomcat-6.0.29/bin/shutdown.sh
sleep 10
kill -9 `ps -aef | grep -i tomcat | gawk {'print $2'} | head -n 1`
kill -9 `ps -aef | grep -i tomcat | gawk {'print $2'} | head -n 1`
rm -rf /home/ec2-user/setups/tomcat/apache-tomcat-6.0.29/webapps/ROOT
rm -f /home/ec2-user/setups/tomcat/apache-tomcat-6.0.29/webapps/ROOT.war
cp /home/ec2-user/code/itech/svn/main/src/orkut2facebook/build/release/orkut.war /home/ec2-user/setups/tomcat/apache-tomcat-6.0.29/webapps/ROOT.war
/home/ec2-user/setups/tomcat/apache-tomcat-6.0.29/bin/startup.sh

