/**********************************************
 **
 ** Course: Database Systems
 ** Date: 12/09/2016
 ** Author: Sophia Haire, Aaron Reed, James Guan, Drake Gao, Oladipupo Eke
 ** Description: Final Project
 **
 ***********************************************/
import java.sql.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.List;
import java.io.*;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.nio.charset.Charset;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.Scanner;

//Note: In cases where error handling is employed, there is the possibility that the message could return the value null (e.g. an IOException).
public class Project_CommandLine {

    public static void main(String[] args) {
	/*********************************
	 *** Open SQLite JDBC driver
	 *** on error, exit
	 **********************************/

	try {
	    Class.forName("org.sqlite.JDBC");
	}

	catch(ClassNotFoundException ex) {
	    ex.getMessage();
	    System.out.println("Check to make sure that the ojdbc7.jar file is in the lib folder of this application's files.\nAlso check to make sure that the CLASSPATH of this required jar is typed in correctly.");
	    System.out.println("\nThe program is now exiting...");
	    System.exit(1);

	}

	/*********************************
	 *** Call to internal method
	 *** getConnection for a
	 *** connection to the Oracle
	 *** database system
	 **********************************/

	Connection con = null;
	try {
	    con = DriverManager.getConnection("jdbc:sqlite:soap.db");
	} catch(Exception e){
	    System.out.println(e);
	}

	System.out.println("Please enter the full file path of a CSV file to bulk load \ndata or type \"skip\" to start querying: ");
	Scanner userInput = new Scanner(System.in);
	String filename = userInput.nextLine().trim();
	
	Integer result = 0;
	if (!filename.equals("skip")){
	    result = bulkLoad(filename, args);
	}

	while (!result.equals(0)){
	    System.out.println();
	    System.out.println("Please enter the full file path of a CSV file to bulk load \ndata or type \"skip\" to start querying: ");
	    filename = userInput.nextLine().trim();
	    result = bulkLoad(filename, args);
	}

	System.out.println();

	String query = "";
	System.out.println("Please enter a query to execute or \"quit\" to quit: ");
	query = userInput.nextLine().trim();

	while (!query.equals("quit")){

	    try {
		Statement stmt = con.createStatement();
		if (query.charAt(0) == 's'){
		    
		    ResultSet rs = stmt.executeQuery(query);
		    ResultSetMetaData metadata = rs.getMetaData();
		    int columnCount = metadata.getColumnCount();
		    while ( rs.next() ) {
			String row = "";
			for (int i = 1; i <= columnCount; i++) {
			    row += rs.getString(i) + ", ";
			}
			System.out.println(row);
		    }
		}
		else{
		    stmt.executeUpdate(query);
		    System.out.println("Executed successfully.");
		}
	    }

	    catch (SQLException se){
		//If the message was printed out here, (System.out.println(se.getMessage())), it would show "ORA-00904: "<nameOfInvalidIdentifier>": invalid identifier"
		se.getMessage();
		System.out.println("Unable to list result." + "\n" + "Check to make sure the query contains valid attribute(s) and/or table value names.");

	    }

	    System.out.println("Please enter a query to execute or \"quit\" to quit: ");
	    query = "";
            query = userInput.nextLine().trim();
	    /* end of method main, exit to system */
	}

	try{
	    con.close();
	}
	catch (SQLException se ){
            //If the message was printed out here, (System.out.println(se.getMessage())), it would be of the following syntax: <error_message>:<extra_info>
	    se.getMessage();
	    System.out.println("Unable to close connection.\n");
	    System.out.println("The program is now exiting...");
	    System.exit(1);

	}


    }
    /***********************************
     *** Method getConnection
     ***
     ***********************************/
    public static Connection getConnection(String[] args) {
	String userLogin = null, userPasswd = null;
	if (args.length < 1) {
	    System.err.println("usage :: java cmsc461 <username> <passwd>");
	    System.exit(1);
	}
       
	Connection con = null;
	try {
	    con = DriverManager.getConnection("jdbc:sqlite:" + args[0]);
	    System.out.println("Connected to SQLite3.");
	}
	catch (SQLException se ){
	    //If the message was printed out here, (System.out.println(se.getMessage())), it would be of the following syntax:            \<error_message>:<extra_info>
	    se.getMessage();
	    System.out.println("Unable to connect to SQLite3.\n");
	    System.out.println("The program is now exiting...");
	    System.exit(1);
	}
	return con;
    }

    /********************************
     *** Method bulkLoad
     ***
     ********************************/
    public static Integer bulkLoad(String filename, String[] args){

	BufferedReader br = null;
        String line = "";
        String csvSplitBy = ";";
        try {
            br = new BufferedReader(new FileReader(filename));
	    int lineNumber = 0;
	    String[] commands = new String[1000];
            while ((line = br.readLine()) != null) {
                commands = line.split(csvSplitBy);
            }

	    Connection con = getConnection(args);

	    try {

		con.setAutoCommit(false);

		for (String command : commands){

       		    Statement stmt = con.createStatement();
		    stmt.executeUpdate(command);
		}

		con.commit();
		System.out.println("The data from the CSV file should now be loaded into the specified table(s).\nConstruct queries to verify the data was successfully loaded.");
	    }
	    catch (SQLException se){
		//If the message was printed out here, (System.out.println(se.getMessage())), it would be of the following syntax:\<error_message>:<extra_info>
		se.getMessage();
	
		System.out.println("Unable to bulk load data from selected CSV file.\n");
		try {
		    System.err.print("Transaction is being rolled back.\nOne or more of the CSV file's transactional components is invalid and cannot be loaded into one or more of the tables.\n");
		    con.rollback();
		    System.out.println("The program is now exiting...");
		    System.exit(1);
		}

		catch(SQLException excep) {
		    //If the message was printed out here, (System.out.println(excep.getMessage())), it would be of the following syntax:\<error_message>:<extra_info>
		    excep.getMessage();
		}

	    }

	    try{
		con.close();
	    }
	    catch (SQLException se){
		//If the message was printed out here, (System.out.println(se.getMessage())), it would be of the following syn                tax:\<error_message>:<extra_info>
		se.getMessage();
		System.out.println("Unable to close connection.\n");
		System.out.println("The program is now exiting...");
		System.exit(1);

	    }

        } catch (FileNotFoundException e) {
	    //If the message was printed out here, (System.out.println(se.getMessage())), it would be of the following syntax:            \<error_message>:<extra_info>
	    e.getMessage();
	    System.out.println("Invalid file name.");
	    return 2;
        } catch (IOException e) {
            //If the message was printed out here, (System.out.println(se.getMessage())), it would be of the following syntax:\<error_message>:<extra_info>
	    e.getMessage();
	    System.out.println("I/O Exception.");
	    return 3;
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {

		    //If the message was printed out here, (System.out.println(se.getMessage())), it would be of the following                    syntax:\<error_message>:<extra_info>
		    e.getMessage();
		    System.out.println("I/O Exception.");
		    return 4;
		}
            }
        }

	return 0;
    }



    /* end of class */
}