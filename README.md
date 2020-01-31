# Normannis Member Tracker 

## An app to manage members in Normannis groups. 

  

### What it does: 

The end user can manage members in a group, including their name and membership status. 

The controller of the application can also manage groups. 

  

### How to use: 

* All members require a unique first and second name combination, and are then given true/false values related to their equipment and membership status. 

* By default, these true/false options are set to false but can then be edited within the front end.  The names can also be edited from here. 

* Adding and deleting members is simple with the use of click buttons at the bottom of the members' table.   

* Whilst the placeholder names ("First name here" and "Second name here") are in place, the user cannot return to the group table or add another member to the group. 

* Returning to the group list is again accomplished with a button at the bottom of the members' table. 

  

  

### Design information: 

* The tables are created within tabulator, link below, and can be resized and sorted by the user.  Click to edit any value on the members' table.   

*  Written in Java using SpringBoot 

  

  

### Requirements: 

#### Essential: 

* Spring Boot Starter Data JPA 

* Spring Boot Starter Web 

* Spring Boot Starter Tomcat 

* Spring Boot Maven Plugin 

* H2 / SQL database 

  

#### Testing: 

* Junit5 

* Spring Boot Starter Test 

* Selenium 

  

  

  

  

  

  

For testing and demonstration purposes, the application currently uses a H2 Database.   

Upon deployment, SQL will be used. 

  

  

  

  

### Upcoming features: 

* Login function 

* Allocating each group to a region for improved usability at scale 

   

  
### Testing
As of latest update, test coverage is 84% by lines of code, and 56% by classes.
Unit tests and integration tests are performed on CRUD functionality with no failures.
  
### Installation and Configuration
The reccomended setup is using AWS.
Once an AWS free-tier account is created, the follow steps will run this app on their services.
#### First, create a database in RDS.
From the RDS dashboard, click the Create database button.  https://eu-west-2.console.aws.amazon.com/rds/home?region=eu-west-2#dashboard
Use Standard Create and MySQl 8.0.16.  Ensure that the free-tier is selected if you do not wish to pay AWS.
Give your RDS instance a unique to you name, along with a master username and password.
Ensure under Connectivity, the RDS is connected to the Default VPC; and that under Database authentication, password and IAM database authentication is selected.  All other settigns can be left as default.  
Finish by clicking Create database.


#### Second, create an EC2 instance.  
Navigate to the AWS EC2 dashboard and click Launch Instance.
The reccomended settings are Ubuntu 18.04 and free-tier for all hardware settings.
Download a new private key and save it somewhere secure.

#### Third, assign it RDS permissions.

Under the action menu, hover over Instance Settings and then click Attach/Replace IAM Role.
Click Create a new role, this will open a new tab in your browser to create a new IAM role.
Click create role, and select AWS serrvice and EC2; then click Next: Permissions.
Under permissions, search for and add rdsconnect and AmazonRDSFullAccess.
Finish the IAM role without changing anything else and then assign it to your backend EC2.

####  Fourth, configure the EC2 instance.

Once the instance is running, connect to it through the terminal.  Click the connect btton on AWS for guide.

Before proceding with the back-end configuration, run the following command:
  mysql -h DatabaseEndpoint -P 3306 -u admin -p
replacing DatabaseEndpoint with the endpoint of your database, found in the RDS console on AWS.
This will launch a MySQL console in your terminal, you will be prompted for the apssword you made earlier.
Then run this command:
  create database Normannis_DB
Your database is now ready for your backend to be configured.

When in the VM, install docker and create and run the backend of the projet with the following commands:
  curl https://get.docker.com | sudo bash
  sudo systemctl start docker
  sudo systemctl enable docker
  sudo usermod -aG docker $USER
  git clone -d docker https://github.com/JacobsChris/membershipapp.git
  cd membershipapp
  docker build -t back-end . 
  docker container run --restart always --name Back_End -p 9632:8080 -d back-end
  
Check that the backend is now running by using: docker ps
You can check that the backend is working correctly by navigating to: http://VM-IP:9632/MembershipApp/member/getAll
where you replace VM-IP with the public IPv4 address of your EC2 instance.  If everythig is working correctly, your browser will diplay [] assuming you have not added anything to your database.

#### Fifth, assuming your EC2 instance is working correctly, 
Use the EC2 console to stop it and then restart it.  Ensure that the docker container starts again automatically.

#### Sixth, create an image of your EC2.
Navigate to your instances in the EC2 console.  Select your backend instance, and consider naming it.
Click on the dropdown action button, hover over Image and click Create Image.
Give your image a name that identifies it, and then click create.

#### Seventh, create an autoscaling group for this image.
Navigate to Auto-Scaling Groups in the EC2 console.  Click create Auto Scaling Group and then Launch Configuration.
Select the image you wish to launch from and then click the enxt button.
Name your group, and ensure the default network and all subnets are selected.
Use scaling policies to dictate the size of the scaling group.  The reccomended settings are between 1 and 2 instances, witha  target value of 50 and that instances need 120 seconds to warm up.
Do not change other settings.

#### Eigth, create a load balancer.
Again from the EC2 area, navigate to Load Balancers.
Click the create a load balancer button, and then Application Load Balancer.
Again, name your load balancer.  Ensure it is internet-facing and has an ipv4 address.  
Change the port to 9632 for the backend, and ensure the load balancer is on the default VPC and all subnets.
The default security group is reccomended.
For the routing, create a new target group and set the port to 9632.
Do not register any targets to the target group at this time.
Finish the completion wizard.

#### Ninth, wait.
It will take some time for the load balancer to be ready.

#### Tenth, navigate back to the autoscaling group create and click actions and then edit.
Scroll down to target groups and add it to the the target group create with the load balancer.
If all has been done correctly, the load balancer and auto scaling group will now replace the lone backend EC2 instance.
Test that everything is working by navigating to http://LoadBalancer:9632/MembershipApp/member/getAll
where LoadBalancer is replaced with the DNS name of your load balancer, found on its console.  If everything works correctly, your browser will display a [].

#### Eleventh, repeat these steps with your front end.
Repeat steps 2 to 10 with the front end with some changes:
When making an EC2 instance, there is no need to access the database again so yuo can skip step 3.
The code is now:
  curl https://get.docker.com | sudo bash
  sudo systemctl start docker
  sudo systemctl enable docker
  sudo usermod -aG docker $USER
  git clone -d docker https://github.com/JacobsChris/membershipapp.git
  cd membershipapp
  docker build -t front-end -d DockerfileFrontEnd . 
  docker container run --restart always --name Front_End -p 90:80 -d front-end
  
Whenever the steps mention a port, now use port 90 instead of 9632.  
Your EC2 instance can use the default security and roles instead of the specific role the back end requires.
  role

README last updated 31/01/2020

  

  

Tabulator documentation of the version in use:  http://tabulator.info/docs/4.5 

 
