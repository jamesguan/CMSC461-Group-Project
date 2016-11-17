<!DOCTYPE html>
<html>

  <head>

	 <title>SOAP System Web Application</title>

	 <link rel="stylesheet" type="text/css" href="grp_css.css">

  </head>

  <body>

    <?php
     $s_tableSelected = $_POST['simple_Query_Table'];
     $c_table1Selected = $_POST['complex_Query_Table1'];
     $c_table2Selected = $_POST['complex_Query_Table2'];
    ?>

    <h1>System for Occupancy Agreement Processing (SOAP)<br/>Application</h1>

    <!-- if line 27 (i.e. the line with "$_SERVER["PHP_SELF]", don't forget to take out the # before 
        the echo htmlspecialchars too) is uncommented and line 26 is commented instead, testing using 
        MAMP for instance reveals that the the app captures the selected table from each dropdown and 
        stores it in the designated variable based on the type of query.-->
    <form name="queryEntries" action="result.php" method="post">
    <!--form name="queryEntries" action="<?php #echo htmlspecialchars($_SERVER["PHP_SELF"]);?>" method="post"--> 

    Choose Data to Load: <button id="upload_button">Upload</button>

    <div id="main_layout">

      <div class="centerText">

        <h2>Simple Query</h2>
        <i>(Simple queries are any SQL queries that do not have any joins or set operations)</i><br/>

        Form your simple query using the dropdown menus below: <br/><br/>

        Choose your table:<br/>
        <select name="simple_Query_Table" class="dropdownTableOptions">
          <option value="" selected disabled>-- Select a table --</option>
          <option value="CustomerAgency"> Customer Agency</option>
          <option value="CustomerAgency_has_RentalAgreement"> Customer Agency has Rental Agreement</option>
          <option value="Office">Office</option>
          <option value="RentalAgreement">Rental Agreement</option>
        </select>

        <br/><br/>

        <input type="submit" class="submit_button" value="Submit">

      </div>

      <br/>

      <hr/>

      <div class="centerText">
        <h2>Complex Query</h2>
        <i>(Complex queries are any SQL queries that involve joins or set operations)</i><br/>

        Form your complex query using the dropdown menus below:<br/><br/>

        Choose your first table:<br/>
        <select name="complex_Query_Table1">
          <option value="" selected disabled>-- Select a table --</option>
          <option value="CustomerAgency1"> Customer Agency</option>
          <option value="CustomerAgency_has_RentalAgreement1"> Customer Agency has Rental Agreement</option>
          <option value="Office1">Office</option>
          <option value="RentalAgreement1">Rental Agreement</option>
        </select>
      
        <br><br>

        Choose your second table:<br/>
        <select name="complex_Query_Table2">
          <option value="" selected disabled>-- Select a table --</option>
          <option value="CustomerAgency2"> Customer Agency</option>
          <option value="CustomerAgency_has_RentalAgreement2"> Customer Agency has Rental Agreement</option>
          <option value="Office2">Office</option>
          <option value="RentalAgreement2">Rental Agreement</option>
        </select>
    
      <br/><br/>

     <input type="submit" class="submit_button" value="Submit">

    </div>

  </div>

 </form>

 <?php 

  echo $s_tableSelected;
  echo $c_table1Selected; 
  echo $c_table2Selected; 

 ?>

 </body>
</html>

