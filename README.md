# membershipapp
An app to manage members in normannis groups.

This app allows the controller of the application to add and otherwise manager groups.
Anyone can then manage members in these groups through the website.

All members require a unique frist and second name combination, and are then given true/false values related to their equipment and meberhsip status.
By default, these true/false options are set to false but can then be editted within the front end.  The names can also be editted from here.
Adding and deleteing members is simple with the use of click buttons at the bottom of the members' table.  
Whilst the placeholder names ("First name ehre" and "Second name here") are in place, the user cannot return to the group table or add another member to the group.

Returning to the group list is again accomplished with a button at the bottom of the members' table.

The tables are created within tabualtor, link below, and can be resized and sorted by the user.  Click to edit any value on the members' table.  
Group management by the end-user is not currently supported.




For testing and demonstration purposes, the application currently uses a H2 Database.  
Upon deployment, SQL will be used.




Upcoming features:

  Login function
  Allocationg each group to a region for imrpvoed usabiltiy at scale
  



README last updated 15/01/2020


Tabulaor documentation of version in use:  http://tabulator.info/docs/4.5
