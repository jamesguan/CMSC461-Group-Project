/**********************************************
  **
  ** Course: Database Systems
  ** Date: 03/18/2015
  ** Author: George Ray
  ** Description: Student starter code for JDBC
  **
  ***********************************************/
import java.sql.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.List;
import java.io.IOException;
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
	    Class.forName("oracle.jdbc.driver.OracleDriver");
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
	Connection con = getConnection(args);


	//This part does not work yet
	System.out.println("Please enter the full file path of a CSV file to bulk load data: ");
	Scanner userInput = new Scanner(System.in);
	String filename = userInput.next();
	String tablename = "";
	
	Integer result = bulkLoad(filename, tablename);
	//

	System.out.println("");

	String query = "";
	System.out.println("Please enter a query to execute or \"quit\" to quit: ");
	query = userInput.next();

	while (!query.equals("quit")){
	    
	    try {
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("select table_name from user_tables");
		while ( rs.next() ) {
		    String name = rs.getString(1);
		    System.out.println(name);
		}
	    }
	    
	    catch (SQLException se ){
		System.out.println("Unable to list result");
		se.printStackTrace();
		System.exit(1);
	    }
	    try{
		con.close();
	    }
	    catch (SQLException se ){
		System.out.println("Unable to close connection");
		se.printStackTrace();
		System.exit(1);
	    }

	    System.out.println("Please enter a query to execute or \"quit\" to quit: ");
            query = userInput.next();
	    /* end of method main, exit to system */
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
    public static Integer bulkLoad(String filename, String tablename){
	
	/*BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";

	if (tablename == "office"){
	    String[][] attributes = new String[1000][3];
	}
	else if (tablename == "customeragency"){
	    String[][] attributes = new String[1000][5];
	}
	else if (tablename == "customeragency_has_rentalagreement"){
	    String[][] attributes = new String[1000][2];
	}
	else if (tablename == "rentalagreement"){
	    String[][] attributes = new String[1000][4];
	}
	else{
	    System.out.println("Invalid table name.");
	    return 1;
	}

        try {
            br = new BufferedReader(new FileReader(filename));

	    int lineNumber = 0;
            while ((line = br.readLine()) != null) {
		
                attributes[lineNumber] = line.split(cvsSplitBy);
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

	*/
	
	return 0;
    }

    

    /* end of class */
}