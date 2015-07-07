
<?php

	
	$db=mysql_connect("localhost", "root", "db2013.")  or die ("Could not connect to database"); 
	
	mysql_select_db('kenyan_education', $db) or die('Error selecting database : ' . mysql_error());
		

?>
