#!/bin/bash

cd ~/Documents/AWS\ VM

ssh -i "key.pem" ubuntu@ec2-3-11-111-10.eu-west-2.compute.amazonaws.com "rm membershipapp -rf \
&& docker system prune \
&& git clone -b Docker git://github.com/JacobsChris/membershipapp.git \
&& cd membershipapp \
&& docker build -t back-end . \
&& docker build -t front-end -f DockerfileFrontEnd . \
&& docker network create front-end-back-end \
&& docker run --network app-mysql -d -p 3306:3306 --name=mysqlMemberGroupDatabase --env=\"MYSQL_ROOT_PASSWORD=password\" --env=\"MYSQL_PASSWORD=password\" --env=\"MYSQL_DATABASE=Normannis_DB\" mysql \
&& sleep 1m \
&& docker container run --restart unless-stopped --network front-end-back-end --name Front_End -p 90:80 -d front-end \
&& docker container run --restart always --network front-end-back-end --name Back_End -p 9632:8080 -d back-end "


#ssh -i "MyKeyPair.pem" ubuntu@ec2-3-9-146-137.eu-west-2.compute.amazonaws.com

wget https://s3.amazonaws.com/rds-downloads/rds-combined-ca-bundle.pem
export RDSHOST="normannis.cinmjcjp1jso.eu-west-2.rds.amazonaws.com"
export TOKEN="$(aws rds generate-db-auth-token --hostname normannis.cinmjcjp1jso.eu-west-2.rds.amazonaws.com --port 3306 --region eu-west-2 --username springBoot)"
mysql --host=$RDSHOST --port=3306 --ssl-ca=/home/ubuntu/rds-combined-ca-bundle.pem --enable-cleartext-plugin --ssl-mode=VERIFY_IDENTITY --user=springBoot --password=$TOKEN -e "CREATE IF NOT EXISTS Normannis_DB"

#Things what I done:
#On AWS create two blank EC2 instances.
#Create RDS on AWS
#SSH into one EC2 instance, install docker
#Using docker, install and run backend (see above)
#Ensure that the backend poitns to the RDS on AWS
#Ensure that docker and container both restart and run when the isntance is stopped and restarted
#Create an AMI image of this workign instance in AWS
#Out of this image, create an autoscaling group.
#Create a loadbalancer for the backend
#Assign the loadbalancer to the autoscaling group
#Test that sending a request to the loadbalancer correctly passes it on to an instance of the backend
#SSH into the second EC2 instance, and isntall the front end using similar steps
#Ensure that the front end points at the load balancer
#Once the instance is working, create an image of it
#Create a new loadbalancer and auto scaling group for the front end, and assign them all correctly
#Test pinging the front end through the load balancer wrks
