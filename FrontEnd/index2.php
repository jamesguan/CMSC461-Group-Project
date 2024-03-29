<!DOCTYPE html>
<html>

  <head>

   <title>SOAP System Web Application</title>

   <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>

   <link rel="stylesheet" type="text/css" href="grp_proj_styling.css">

  </head>

  <body>

    <?php
     $s_tableSelected = $_POST['simple_Query_Table'];
     $c_table1Selected = $_POST['complex_Query_Table1'];
     $c_table2Selected = $_POST['complex_Query_Table2'];
    ?>

    <h1>System for Occupancy Agreement Processing (SOAP)<br/>Application</h1>

    <form name="queryEntries" action="result.php" method="post">

    <div id="main_layout">

      <div class="centerText">

        <h2>Simple Query</h2>
       (Simple queries are any SQL queries that fit either of the two syntaxes below):<br/><br/> 
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1. <em>select</em> attributeName <em>from</em> tableName<br/>
            2. <em>drop table</em> tableName<br/><br/>
        Form your simple query below: <br/><br/>

        Choose your table:<br/>
        <select name="simple_Query_Table">
          <option value="" selected disabled>-- Select a table --</option>
          <option value="CustomerAgency"> Customer Agency</option>
          <option value="CustomerAgency_has_RentalAgreement"> Customer Agency has Rental Agreement</option>
          <option value="RentalAgreement">Rental Agreement</option>
          <option value="Office">Office</option>
        </select>

        <br/><br/>
        <div id="file_space">
        Choose Data to Load for Table:<br/> <input type="file" accept=".csv"><br/><br/>
        Erase Table:<br/><button id="erase_button">Erase</button><br/><br/>
        Select Attributes from Table:<br/>
        <select name="simple_Query_Table_Attributes" multiple>
          <option value="" selected disabled>-- Select table attribute(s) --</option>
          <option value="agency_id">agency_id</option>
          <option value="agency_name">agency_name</option>
          <option value="agency_city">agency_city</option>
          <option value="address">address</option>
          <option value="phone_num">phone_num</option>
          
          <option value=""></option>
        </select>

        </div>

        <br/><br/>

        <input type="submit" class="submit_button" name="simpleQuerySubmit" value="Submit Simple Query">

      </div>

      <br/>

      <hr/>

      <div class="centerText">
        <h2>Complex Query</h2>
        (Complex queries are any SQL queries that involve more SQL actions than either erasing a table or selecting attributes from a single table.)<br/><br/>

        Form your complex query by typing it into the field below:<br/><br/>
        <textarea rows="4" cols="50">
        </textarea>
      
        <br><br>

     <input type="submit" class="submit_button" name="complexQuerySubmit" value="Submit Complex Query">

    </div>

  </div>

 </form>

 <script>

 /*function uploadCSVFile(){




 }*/

 </script>

 </body>
</html>

