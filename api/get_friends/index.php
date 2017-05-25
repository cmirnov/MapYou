<?php
include "../../core.php";

RequestHandler::Listener(function ($request) {

    if (!isset($request['list']))
        throw new Exception('Incorrect fields');

    // TODO: check list
    $friendsList = explode(",", sanitizeString($request['list']));

    global $_CONFIG;
    $mysql = new DB($_CONFIG['db']['host'], $_CONFIG['db']['user'], $_CONFIG['db']['password'], $_CONFIG['db']['name'], $_CONFIG['db']['charset']);
    $q = $mysql->Query("SELECT id, X(position) as x, Y(position) as y  FROM users WHERE id in ('" . implode("', '", $friendsList) . "') ");

    $onlineFriends = array();
    while ($row = $mysql->FetchAssoc($q))
    {
        array_push($onlineFriends, array(
            "id" => $row['id'],
            "x" => $row['x'],
            "y" => $row['y']
        ));
    }


        print_r(makeResponse("ok", $onlineFriends));

});