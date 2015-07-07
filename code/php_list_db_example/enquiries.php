<?php
include_once 'connect.php';



$post = file_get_contents('php://input');//php://input is a read-only stream that allows you to read raw data from the request body.

$data = json_decode($post, true);//the post is decoded into an array insted of an object
// print_r($data);exit;

$response = array("success" => 0, "error" => 0);


//check if all the four parameters we expect have been supplied
if(count($data) < 4){
	$response["error"] = 1;
	$response["error_msg"] = "You need to pass all parameters";
	echo json_encode($response);
}

$university_id = $data['university_id'];
$email = $data['email'];
$subject = $data['subject'];
$message = $data['message'];


$result = mysql_query("INSERT INTO enquiries (university_id, email, subject, message) VALUES ('$university_id', '$email', '$subject', '$message')");
if ($result) {
	$response["success"] = 1;
	$response["msg"] = "Successfully submitted your enquiry. We will get back to you shortly";
	echo json_encode($response);
} else {
	$response["error"] = 1;
	$response["error_msg"] = "Error in submitting your enquiry. Try again later";
	echo json_encode($response);
}

?>