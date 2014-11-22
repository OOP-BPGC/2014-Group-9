Sr. No.				Name				Student ID					  Roles						     	Role-based Contributions		Overall Cntrbt(%)
1.	    		SAURABH SWAMI  	   	   2013A7PS040G      		 	Unit Tests  							     0                          5
2.   	RAJASEKHAR REDDY CHINNAVULA    2013A7PS013G				 	Unit Tests                                  10                          10
3.				 ARNAB DAS			   2013A7PS020G			 CRC Cards and UML Diagrams                         18                          15
4.			VARTAK CHIRAG NILESH 	   2012A3PS242G		   	     Code Development                               18                          18
5. 			  HIMANSHU JETHANI		   2012A8PS364G		   CRC Cards, UML Diagrams, Unit Tests                  18                          15
6.			 ANANYA GIRRAJ GARG        2013A7PS067G		Project Reqmnts, Use Cases, Code Development            18                          15
7.	   SIDDHARTH A SANKARAN NAMBISAN   2012B3A7519G		Project Reqmnts, Use Cases, Project Manager             18                          22
--------------------------------------------------------------------------------------------------------------------------------------------------


Topic						Number in 						Code
						Stage-2 Submission			Complete	Incomplete
--------------------------------------------------------------------------------------------------------------------------------------------------
Use Cases						5				  	  yes 			-
Classes 						4					  yes			-
Packages					 	2					  yes			-
Sequence diagrams				1					  yes			-
Unit Tests 						4					  yes			-


Topic						Number				Test Status
						   of tests			  Pass	      Fail
--------------------------------------------------------------------------------------------------------------------------------------------------
Unit Tests submitted at 	  6				   yes 			-
the end of Stage-2								

Tests added later			  -					-			-
--------------------------------------------------------------------------------------------------------------------------------------------------


Software requirements:

1. Java SE7
   JRE system library minimum 1.5
2. Relational Database Management system  MySQL
3. Apache Ant (if you want to reproduce the class files)

Special libraries:

1. easymock-3.2
2. mysql-connector-java-5.1.34-bin

Installation Instructions: 

1. MySQL

Install MySQL: http://dev.mysql.com/downloads/mysql/


2. Apache ANT
Install ANT: https://ant.apache.org/manual/install.html#installing
(Set ANT_HOME, JAVA_HOME, and PATH.)


3. Creating Database and Class files:
(i) Change the working directory to the folder of the project.
(ii) Type <<ant>>, in the terminal.
     The build should be succcessful and the bin is now populated.
(iii)Next, <<java -jar FirstUse.jar>> <passwordToMySQLRootAccess>
        This creates the database and the table required for the software to work.
(iv) Next, <<java -jar Program.jar>>
    This should get your program running.

Executing program:
This last step should be repeated each time that the program is required to run.
On linux based systems, we have created a shortcut by which just typing <<./Program>> will run the Program.jar file

Login Instructions:


If you want to log in as the admin : Username: admin ; Password : admin
If you want to log in as CC In Charge : Username: CC-In-Charge ; Password : CC-In-Charge
If you want to log in as ARC IN CHarge : Username : ARC-In-Charge ; Password : ARC-In-Charge

If you want to create new logins for students and faculty, log in as admin and register new users by following the instructions.
