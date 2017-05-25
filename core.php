<?php
include_once "config.php";


class DB
{
    private $dbHandler = NULL;
    private $dbType = "mysql";

    public function __construct($host, $user, $pass, $name, $charset)
    {
        $dsn = '' . $this->dbType . ':host=' . $host . ';dbname=' . $name . ';charset=' . $charset;
        try
        {
            $this->dbHandler = new PDO($dsn, $user, $pass);
        }
        catch (PDOException $e)
        {
            $this->dbHandler = NULL;
            throw new Exception("Couldn't connect to db");
        }
        return $this->dbHandler;
    }

    public function __destruct()
    {
        $this->close();
    }

    public function Query($q)
    {
        return $this->dbHandler->query($q);
    }

    public function Exec($q)
    {
        return $this->dbHandler->exec($q);
    }

    public function FetchRow(PDOStatement $queryHandler)
    {
        return $queryHandler->fetch(PDO::FETCH_NUM);
    }

    public function FetchAssoc(PDOStatement $queryHandler)
    {
        return $queryHandler->fetch(PDO::FETCH_ASSOC);
    }

    public function NumRows(PDOStatement $queryHandler)
    {
        return $queryHandler->rowCount();
    }

    public function Close()
    {
        $this->dbHandler = NULL;
    }
}


class Point
{
    private $latitude = "";

    private $longitude = "";

    public function __construct($latitude, $longitude)
    {
        $this->latitude = $latitude;
        $this->longitude = $longitude;
    }

    public function GetLatitude()
    {
        return $this->latitude;
    }

    public function GetLongitude()
    {
        return $this->longitude;
    }

    public function GetSqlObject()
    {
        return "Point(" . $this->latitude . " " . $this->longitude . ")";
    }
}



class User
{
    private $id = "";

    private $exist = false;

    private $geoPosition = null;

    public function __construct($id, Point $geoPosition)
    {
        $this->id = $id;
        $this->geoPosition = $geoPosition;
    }

    public  function GetId()
    {
        return $this->id;
    }

    public  function GetGeoPosition()
    {
        return $this->geoPosition;
    }

    public function Exist()
    {
        global $_CONFIG;
        $mysql = new DB($_CONFIG['db']['host'], $_CONFIG['db']['user'], $_CONFIG['db']['password'], $_CONFIG['db']['name'], $_CONFIG['db']['charset']);
        $userNum = $mysql->NumRows($mysql->Query("SELECT id FROM users WHERE id = '" . $this->id . "'"));
        $mysql->Close();
        if ($userNum == 1)
        {
            $this->exist = true;
            return true;
        }
        else
        {
            $this->exist = false;
            return false;
        }
    }

    static private function updateBuildingCounters(User $user)
    {
        global $_CONFIG;
        $mysql = new DB($_CONFIG['db']['host'], $_CONFIG['db']['user'], $_CONFIG['db']['password'], $_CONFIG['db']['name'], $_CONFIG['db']['charset']);

        $building = $mysql->FetchRow($mysql->Query("SELECT buildings.id, users.last_building FROM buildings, users WHERE Contains(buildings.points, GeomFromText('" . $user->geoPosition->GetSqlObject() . "')) AND users.id = '" . $user->GetId() . "'"));
        $buildingId = $building[0];
        $lastBuilding = $building[1];

        //print_r($user->geoPosition->GetSqlObject() . " " . $buildingId);
        if ($lastBuilding != $buildingId)
            $mysql->Exec("UPDATE buildings SET current_load = current_load + 1 WHERE id = '" . $buildingId . "'");

        $mysql->Close();

        return $buildingId;
    }

    public function Create()
    {
        global $_CONFIG;
        $mysql = new DB($_CONFIG['db']['host'], $_CONFIG['db']['user'], $_CONFIG['db']['password'], $_CONFIG['db']['name'], $_CONFIG['db']['charset']);


        $mysql->Exec("INSERT INTO users (id, last_building, position) VALUES ('" . $this->id . "', '', GeomFromText('" . $this->geoPosition->GetSqlObject() . "'))");
        $this->exist = true;

        $mysql->Close();
    }

    public function UpdateGeoPosition()
    {
        if (!$this->Exist())
            throw new Exception("User doesn't exist");

        global $_CONFIG;
        $mysql = new DB($_CONFIG['db']['host'], $_CONFIG['db']['user'], $_CONFIG['db']['password'], $_CONFIG['db']['name'], $_CONFIG['db']['charset']);

        $building = $mysql->FetchRow($mysql->Query("SELECT id FROM buildings WHERE Contains(buildings.points, GeomFromText('" . $this->geoPosition->GetSqlObject() . "'))"));
        $currentBuildingId = $building[0];
        //$lastBuilding = $building[1];

        //if ($buildingId != $lastBuilding)
        $mysql->Exec("UPDATE buildings SET current_load = GREATEST(current_load - 1, 0) WHERE id IN (SELECT last_building FROM users WHERE id = '" . $this-> id . "')");

        $mysql->Exec("UPDATE users SET last_building = '" . $currentBuildingId . "' WHERE id = '" . $this->id . "'");

        if (!empty($currentBuildingId))
            $mysql->Exec("UPDATE buildings SET current_load = current_load + 1 WHERE id = '" . $currentBuildingId . "'");

        //$mysql->Exec("UPDATE users SET position = GeomFromText('" . $this->geoPosition->GetSqlObject() . "') WHERE id = '" . $this->id . "'");



        //self::updateBuildingCounters(new User($this->GetId(), $this->GetGeoPosition()))

        $mysql->Close();
    }

}


abstract class RequestHandler
{
    static public function Listener($handler)
    {
        try
        {
            self::checkSecurity($_GET);
            $handler($_GET);
        }
        catch (Exception $e)
        {
            print_r(makeResponse("error", $e->getMessage()));
        }
    }

    static private function checkSecurity(array $requestFields)
    {
        global $_CONFIG;

        if (!isset($requestFields['security']))
            throw new Exception('Incorrect fields');


        $hash = $requestFields['security'];
        unset($requestFields['security']);

        $fields = implode("", $requestFields);
        if (sha1($fields . $_CONFIG["security"]["salt"]) != $hash)
            throw new Exception('Validation failure ' . sha1($fields . $_CONFIG["security"]["salt"]));
    }

    static public function JsonResponse($status, $msg)
    {
        $response = array(
            "status" => $status,
            "message" => $msg
        );
        print_r(json_encode($response, true));
    }

}



function makeResponse($status, $msg)
{
    $response = array(
        "status" => $status,
        "message" => $msg
    );
    return json_encode($response, true);
}


function sanitizeString($var)
{
    $var = htmlspecialchars($var, ENT_QUOTES);
    $var = strip_tags($var);
    return stripslashes($var);
}

?>