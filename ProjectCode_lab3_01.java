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
public class cmsc461 {
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
 try {
Statement stmt = con.createStatement();
ResultSet rs = stmt.executeQuery("select table_name from user_tables");
while ( rs.next() ) {
String name = rs.getString(1);
System.out.println(name);
}
 }
 catch (SQLException se ){
System.out.println("Unable to list user tables");
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
/* end of method main, exit to system */
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
/* end of class */
}