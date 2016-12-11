/****************************************************************************************************************************************************
 **
 **                                
 **                 System for Occupancy Agreement Processing (SOAP) Application 
 **                               Readme Information
 ** 
 **
******************************************************************************************************************************************************

This System for Occupancy Agreement Processing (SOAP) program application uses Java/JDBC along with SQLite3 to perform certain user-specified functions. The program is capable of the following: accessing a SOAP database which is created using SQLite3 commands, bulk loading data into tables from Comma-Separated Values (CSV) files, taking in queries to erase tables, and insert as well delete a record(s) from one or more of the SOAP databases’ tables.

/*****************************************************************************************************************************************************

Below are instructions for how to make sure the SOAP program is set up correctly, covering everything from setting up the SQLite3 database and checking to make sure the tables have been created, to actually being able to run the SOAP program itself, specifying the database that was created prior to launching the program. 

Please note: 
-Make sure that the sqlite3.exe, sqlite-jdbc-3.15.1.jar, Project_CommandLine.java, soap.sql, and sample_soap_file.csv files are all in the same 
 directory when proceeding through the following steps. 

-Make sure all previous soap.sql files are removed and that the most recent soap.sql file is used. 

Load SQLite3 database
/===================/
- sqlite3 soap.db  (creates soap.db if one doesn't already exist, uses soap.db if it exists)
- .read soap.sql (once sqlite3 prompt appears)
- .tables (make sure all tables are in the DB)
- .schema (want to see schema)

/*********************/

Run the Java Program
/===================/
- javac Project_CommandLine.java
- java -classpath ".:sqlite-jdbc-3.15.1.jar" Project_CommandLine soap.db
- Specify the CSV file
- Run queries
- Quit program when desired

/*****************************************************************************************************************************************************
Additional Note

Please take note of the following when running the SOAP program:
-When running a CSV file, make sure that for the insert statement, the table that is having a tuple inserted into it contains its table field names. 
 e.g. insert into office(‘office_name’, ‘office_city’, ‘sqfootage’) values(‘Office 1’, ‘Tinkerton’, 400);

/****************************************************************************************************************************************************
Areas for Future Improvement: 

-The CustomerAgency_has_RentalAgreement table takes in two fields, CustomerAgency_agency_id and RentalAgreement, which both comprise the primary key   
 as well as foreign keys to the CustomerAgency and RentalAgreement tables, respectively. When using an insert statement for the 
 CustomerAgency_has_RentalAgreement table, the CSV file can currently accept hard-coded values even if these values do not actually exist in the 
 database at this time. In the future, control mechanisms could be put in place to help ensure that such insert statements could not be inserted into 
 the CustomerAgency_has_RentalAgreement table if the CustomerAgency_agency_id and RentalAgreement specified in the insert statement do not actually 
 exist in the CustomerAgency and RentalAgreement tables being referenced. 

-For increased user-friendliness and aesthetic value, this SOAP command-line based program could be converted into a Web application. The web application could rely on user interaction to get its values. For instance, the user could click on a button to select their CSV from a file directory and manually enter in their queries.  