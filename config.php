<?php

header('Content-Type: application/json; charset=utf-8');

// init global config 
$_CONFIG = array();



// sql config 
$_CONFIG["db"]["host"] = "localhost";
$_CONFIG["db"]["port"] = "3306";
$_CONFIG["db"]["user"] = "mapyou";
$_CONFIG["db"]["password"] = "mapyou";
$_CONFIG["db"]["charset"] = "utf8";
$_CONFIG["db"]["name"] = "mapyou";


// security
$_CONFIG["security"]["salt"] = "$!ASD($@%!";

 

//print_r($CONFIG);

?>