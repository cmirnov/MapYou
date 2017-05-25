<?php
include "../../core.php";

RequestHandler::Listener(function ($request) {

    if (!isset($request['id']) || !isset($request['latitude']) || !isset($request['longitude']))
        throw new Exception('Incorrect fields');

    $id = sanitizeString($request['id']);
    $latitude = sanitizeString($request['latitude']);
    $longitude = sanitizeString($request['longitude']);

    $user = new User($id, new Point($latitude, $longitude));

    try
    {
        $user->UpdateGeoPosition();
    }
    catch (Exception $e)
    {
        $user->Create();
        $user->UpdateGeoPosition();
    }

    RequestHandler::JsonResponse("ok", "");

});