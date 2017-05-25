<?php
include "../../core.php";

RequestHandler::Listener(function ($request) {

    if (!isset($request['point1_latitude']) || !isset($request['point1_longitude']) || !isset($request['point2_latitude']) || !isset($request['point2_longitude']))
        throw new Exception('Incorrect fields');


    $point1 = new Point((double)$request['point1_longitude'], (double)$request['point1_longitude']);
    $point2 = new Point((double)$request['point2_longitude'], (double)$request['point2_longitude']);



    global $_CONFIG;
    $mysql = new DB($_CONFIG['db']['host'], $_CONFIG['db']['user'], $_CONFIG['db']['password'], $_CONFIG['db']['name'], $_CONFIG['db']['charset']);
    $q = $mysql->Query("SELECT id, current_load, max_load, AsText(points) FROM buildings");
    $buildings = array();
    while ($row = $mysql->FetchAssoc($q))
    {
        //print_r($row);
        $polygon = explode(",", substr(substr($row['AsText(points)'], 9), 0, -2));
        $points = array();
        foreach ($polygon as $p)
        {
            $axis = explode(" ", $p);
            array_push($points, array(
                "x" => $axis[0],
                "y" => $axis[1]
            ));
        }

        array_push($buildings, (array(
            "id" => $row['id'],
            "current_load" => $row['current_load'],
            "max_load" => $row['max_load'],
            "points" =>  $points

        )));
    }

    $mysql->Close();

    print_r(makeResponse("ok", $buildings));

});