<?php
include "core.php";

if (isset($_GET['id']) && isset($_GET['type']) &&  $_GET['type'] == "update_geo_position" && isset($_GET['latitude']) && isset($_GET['longitude']))
{
    $id = $_GET['id'];
    $axis_x = $_GET['latitude'];
    $axis_y = $_GET['longitude'];

    /*
     * TODO:
     *  validate user data
     */

    $user = new User($id, new Point($axis_x, $axis_y));

    try
    {
        try
        {
            $user->UpdateGeoPostion();
        }
        catch (Exception $e)
        {
            $user->Create();
        }
        print_r(makeResponse("ok", ""));
    }
    catch (Exception $e)
    {
        print_r(makeResponse("error", $e->getMessage()));
    }

}


?>