/**********************************************
 **
 ** Course: Database Systems
 ** Date: 11/28/16
 ** Author: George Ray, Sophia Haire, Aaron Reed, James Guan
 ** Description: Command line JDBC application
 **
 ***********************************************/
import java.sql.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.List;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.nio.charset.Charset;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Project_CommandLine {
    
    public static void main(String[] args) {
	/*********************************
	 *** Open Oracle JDBC driver
	 *** on error, exit
	 **********************************/
	
	try {
	    Class.forName("com.mysql.jdbc.Driver");
	}
	catch(ClassNotFoundException ex) {
	    ex.printStackTrace(System.err);
	    System.exit(1);
	}
	
	/*********************************
	 *** Call to internal method
	 *** getConnection for a
	 *** connection to the Oracle
	 *** database system
	 **********************************/
	try{
	    Connection con = DriverManager.getConnection("jdbc:mysql://localhost/feedback?user=root&password=");
	    
	    
	    //This part does not work yet
	    System.out.println("Please enter the full file path of a CSV file to bulk load data: ");
	    Scanner userInput = new Scanner(System.in);
	    String filename = userInput.nextLine().trim();
	    Integer result = bulkLoad(filename, args);
	    
	    while (!result.equals(0)){
		System.out.println();
		System.out.println("Please enter the full file path of a CSV file to bulk load data: ");
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
		
		catch (SQLException se ){
		    System.out.println("Unable to list result");
		    se.printStackTrace();
		    System.exit(1);
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
		System.out.println("Unable to close connection");
		se.printStackTrace();
		System.exit(1);
	    }
	}
	catch(Exception ex){
	    System.out.println("Oops");
	    System.exit(100);
	}
       	
    }

    /***********************************
     *** Method getConnection
     ***
     ***********************************/
    public static Connection getConnection(String[] args) {
	String userLogin = null, userPasswd = null;
	if (args.length < 2) {
	    System.err.println("usage :: java cmsc461 <username> <passwd>");
	    System.exit(1);
	}
	else {
	    userLogin = args[0];
	    userPasswd = args[1];
	}
	String url = "jdbc:oracle:thin:@studentdb-oracle.gl.umbc.edu:1521/STUDENTDB";
	Connection con = null;
	try {
	    con = DriverManager.getConnection(url, userLogin, userPasswd);
	    System.out.println("Connected to Oracle.");
	}
	catch (SQLException se ){
	    System.out.println("Unable to connect to Oracle.");
	    se.printStackTrace();
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
        String cvsSplitBy = ";";
        try {
            br = new BufferedReader(new FileReader(filename));
	    int lineNumber = 0;
	    String[] commands = new String[1000];
            while ((line = br.readLine()) != null) {
                commands = line.split(cvsSplitBy);
	    }
            
	    
	    try{
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost/feedback?user=root&password=");
		
		for (String command : commands){
		    
		    try {
			System.out.println(command);
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(command);
		    }
		    catch (SQLException se ){
			System.out.println("Unable to list result");
			se.printStackTrace();
			System.exit(1);
		    }
		    
		}
	    	    
		try{
		    con.close();
		}
		catch (SQLException se ){
		    System.out.println("Unable to close connection");
		    se.printStackTrace();
		    System.exit(1);
		}
		catch (Exception ex){
		    System.out.println("Oops");
		    System.exit(100);
		}
	    } catch (Exception ex) {
		System.out.println("Bulkload exception");
	    }
	    
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	    System.out.println("Invalid file name.");
	    return 2;
	} catch (IOException e) {
	    e.printStackTrace();
	    System.out.println("I/O Exception. Sorry about that.");
	    return 3;
	} finally {
	    if (br != null) {
		try {
		    br.close();
		} catch (IOException e) {
		    e.printStackTrace();
		    System.out.println("I/O Exception. Sorry about that.");
		    return 4;
		}
	    }
	}

	return 0;
    }
    
/* end of class */
}


