<?php

header('Content-Type: application/json; charset=utf-8');

// init global config 
$_CONFIG = array();

// paths 
//$_CONFIG["path"]["js"] = "js/";
//$_CONFIG["path"]["style"] = "css/";
//$_CONFIG["path"]["images"] = "css/images/";
//$_CONFIG["path"]["logo"] = "css/images/logo.png";
//$_CONFIG["path"]["icon"]["vk"] = "css/images/vk.png";
//$_CONFIG["path"]["icon"]["fb"] = "css/images/fb.png";


// sql config 
$_CONFIG["db"]["host"] = "localhost";
$_CONFIG["db"]["port"] = "3306";
$_CONFIG["db"]["user"] = "mapyou";
$_CONFIG["db"]["password"] = "mapyou";
$_CONFIG["db"]["charset"] = "utf8";
$_CONFIG["db"]["name"] = "mapyou";


// session configuration
//$_CONFIG["session"]["lifetime"] = 3 * 24 * 3600; // 3 days;


// file upload configuring
/*$_CONFIG['file_upload']['max_number'] = 6;
$_CONFIG['file_upload']['max_file_size'] = 2 * 1024 * 1024; // 4 mb
$_CONFIG['file_upload']['path']['tmp_files'] = "tmp_files" . DIRECTORY_SEPARATOR;
$_CONFIG['file_upload']['path']['files'] = "files" . DIRECTORY_SEPARATOR;
$_CONFIG['file_upload']['extensions'] = array('doc', 'docx', 'pdf', 'jpeg', 'jpg', 'png', 'xlsx', 'xls');
*/




//define ("RESPONSE_OK", "ok");
//define ("RESPONSE_ERROR", "error");
 

//print_r($CONFIG);

?>