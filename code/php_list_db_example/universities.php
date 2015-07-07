<?php 

	include_once 'connect.php';
	//selecting everything from thr database table universities	
	$results = mysql_query("SELECT id, title, description FROM universities");


	// final array for json response
    $universities = array();
    $universities["universities"] = array();


	//loop through the data returned and store in the array $records
	while ($row = mysql_fetch_array($results)) {
		//creates a single entry
		$records = array();
		$records['id'] = (int)$row['id'];
		$records['university_name'] = $row['title'];
		$records['description'] = $row['description'];
		// $records['phone_no'] = $row['phone_no'];
		// $records['website'] = $row['website'];
		// $records['address'] = $row['address'];
		// $records['latitude'] = $row['latitude'];
		// $records['longitude'] = $row['longitude'];

		array_push($universities['universities'], $records);
	}

	// keeping response header to json
    header('Content-Type: application/json');

	//ouput our results as json
	echo json_encode($universities);

?>