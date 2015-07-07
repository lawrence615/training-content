<?php 
include_once 'connect.php';

$university_id = $_GET['university_id'];

$results = mysql_query("SELECT * FROM universities WHERE id = $university_id");


	//loop through the data returned and store in the array $records
	while ($row = mysql_fetch_array($results)) {
		//creates a single entry
		$records = array();
		$records['id'] = (int)$row['id'];
		$records['university_name'] = $row['title'];
		$records['description'] = $row['description'];
		$records['phone_no'] = $row['phone_no'];
		$records['website'] = $row['website'];
		$records['address'] = $row['address'];
		$records['latitude'] = $row['latitude'];
		$records['longitude'] = $row['longitude'];
	}

	// keeping response header to json
    header('Content-Type: application/json');

	//ouput our results as json
	echo json_encode($records);

?>