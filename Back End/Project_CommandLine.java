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
import java.io.FileNotFoundException;
import java.nio.charset.Charset;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Project_CommandLine {


    public static void main(String[] args){

        // Scanner object to read in user input
        Scanner userInput = new Scanner(System.in);

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

        /*
        boolean validInput = false;
        System.out.println("Would you like to grab the connection via commandline arguments? (y/n)")

        while(validInput == false){
            userInput = userInput.nextLine().toUpperCase().trim();
            if (userInput.equals("Y") || userInput.equals("N") || userInput.equals("YES") || userInput.equals("NO")){
                validInput = true;
            }
            else{
                System.out.println("Please enter yes or no or y or n");
            }
        }

        if (userInput.equals("Y")){
            System.out
        }
        */


	      /*********************************
	       *** Call to internal method
	       *** getConnection for a
	       *** connection to the Oracle
	       *** database system
	       **********************************/
	      Connection con = getConnection(args);
	      //This part does not work yet
	      System.out.println("Please enter the full file path of a CSV file to bulk load data: ");
	      String filename = userInput.nextLine().trim();
	      Integer result = bulkLoad(filename, args);

	      while (!result.equals(0)){
	          System.out.println("Please enter the full file path of a CSV file to bulk load data: ");
	          filename = userInput.nextLine().trim();
            result = bulkLoad(filename, args);
	      }

	      String query = "";
	      System.out.println("Please enter a query to execute or \"quit\" to quit: ");
	      query = userInput.nextLine().trim();

	      while (!query.equals("quit")){
	          try {
		            Statement stmt = con.createStatement();
		            ResultSet rs = stmt.executeQuery(query);
		            ResultSetMetaData metadata = rs.getMetaData();
		            int columnCount = metadata.getColumnCount();
		            while ( rs.next() ){
		                String row = "";
		                for (int i = 1; i <= columnCount; i++) {
			                   row += rs.getString(i) + ", ";
		                }
		                System.out.println(row);
	              }
	          }// end try
            catch (SQLException se ){
	              System.out.println("Unable to list result, SQL error\nPlease try again.");
		            se.printStackTrace();
                // Disable exiting from the console just because of an error
		            //System.exit(1);
	          }// end catch

            System.out.println("Please enter a query to execute or \"quit\" to quit: ");
            query = userInput.nextLine().trim();
	          /* end of method main, exit to system */
	      }// end while

	      try{
	          con.close();
	      }
	      catch (SQLException se ){
	         System.out.println("Unable to close connection");
	         se.printStackTrace();
	         System.exit(1);
 	      }
        userInput.close();
    } // end main

    /***********************************
     *** Method getConnection
     ***
     ***********************************/
    public static Connection getConnection(String[] args) {
        Scanner reader = new Scanner(System.in);
	      String userLogin = null, userPasswd = null;
	      if (args.length < 2) {
	          System.err.println("Not enough arguments\nusage :: java cmsc461 <username> <passwd>");
	          System.out.print("Enter login manually: ");
            userLogin = reader.nextLine();
            System.out.print("Enter password manually: ")
            userPasswd = reader.nextLine();
	      }
	      else {
	          userLogin = args[0];
	          userPasswd = args[1];
    	  }
	      String url = "jdbc:oracle:thin:@studentdb-oracle.gl.umbc.edu:1521/STUDENTDB";
	      Connection con = null;
        while (true){
	          try {
	              con = DriverManager.getConnection(url, userLogin, userPasswd);
	              System.out.println("Connected to Oracle.");
                reader.close();
                return con;
  	        }
	          catch (SQLException se ){
	              System.out.println("Unable to connect to Oracle. Wrong login or password?");
	              se.printStackTrace();
                System.out.print("Enter login again: ");
                userLogin = reader.nextLine();
                System.out.print("Enter password again: ")
                userPasswd = reader.nextLine();
	          }


        }
    }// End connection method

    /********************************
     *** Method bulkLoad
     ***
     ********************************/
    public static Integer bulkLoad(String filename, String[] args){
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        try {
            br = new BufferedReader(new FileReader(filename));
	          int lineNumber = 0;
	          String[] commands = new String[1000];
            while ((line = br.readLine()) != null) {
                commands = line.split(cvsSplitBy);
            }

	          Connection con = getConnection(args);

	          for (String command : commands){

		            try {
		                Statement stmt = con.createStatement();
		                ResultSet rs = stmt.executeQuery(command);
      		      }
		            catch (SQLException se ){
                    System.out.println("Unable to list result,\none of the SQL statements are screwed up!");
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
	          }// end for loop

        } // end main try
        catch (FileNotFoundException e) {
            e.printStackTrace();
	          System.out.println("Invalid file name.");
	          return 2;
        }
        catch (IOException e) {
            e.printStackTrace();
	          System.out.println("I/O Exception. Sorry about that.");
	          return 3;
        }
        finally {
            if (br != null) {
                try {
                    br.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
		                System.out.println("I/O Exception. Sorry about that.");
		                return 4;
		            } // end catch
            }// end if
        }// end finally

	      return 0;
    }// end Bulkload method




}// end class
