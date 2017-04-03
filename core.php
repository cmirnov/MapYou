<?php
include_once "config.php";


class DB
{
    private $db_handler = NULL;
    private $db_type = "mysql";

    public function __construct($host, $user, $pass, $name, $charset)
    {
        $dsn = '' . $this->db_type . ':host=' . $host . ';dbname=' . $name . ';charset=' . $charset;
        try
        {
            $this->db_handler = new PDO($dsn, $user, $pass);
        }
        catch (PDOException $e)
        {
            $this->db_handler = NULL;
            throw new Exception('Cant connect to db');
        }
        return $this->db_handler;
    }

    public function __destruct()
    {
        $this->close();
    }

    public function Query($q)
    {
        return $this->db_handler->query($q);
    }

    public function Exec($q)
    {
        return $this->db_handler->exec($q);
    }

    public function FetchRow(PDOStatement $query_handler)
    {
        return $query_handler->fetch(PDO::FETCH_NUM);
    }

    public function FetchAssoc(PDOStatement $query_handler)
    {
        return $query_handler->fetch(PDO::FETCH_ASSOC);
    }

    public function NumRows(PDOStatement $query_handler)
    {
        return $query_handler->rowCount();
    }

    public function Close()
    {
        $this->db_handler = NULL;
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
}



class User
{
    private $id = "";

    private $exist = false;

    private $latitude = "";

    private $longitude = "";

    public function __construct($id, Point $geoPosition)
    {
        $this->id = $id;
        $this->latitude = $geoPosition->GetLatitude();
        $this->longitude = $geoPosition->GetLongitude();
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

    public function Create()
    {
        global $_CONFIG;
        $mysql = new DB($_CONFIG['db']['host'], $_CONFIG['db']['user'], $_CONFIG['db']['password'], $_CONFIG['db']['name'], $_CONFIG['db']['charset']);

        $mysql->Exec("INSERT INTO users (id, latitude, longitude) VALUES ('" . $this->id . "', '" . $this->latitude . "', '" . $this->longitude . "')");
        $this->exist = true;

        $mysql->Close();
    }

    public function UpdateGeoPostion()
    {
        if (!$this->Exist())
            throw new Exception('User are not exist');

        global $_CONFIG;
        $mysql = new DB($_CONFIG['db']['host'], $_CONFIG['db']['user'], $_CONFIG['db']['password'], $_CONFIG['db']['name'], $_CONFIG['db']['charset']);

        $mysql->Exec("UPDATE users SET latitude = '" . $this->latitude . "', longitude = '" . $this->longitude . "' WHERE id = '" . $this->id . "'");

        $mysql->Close();
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

?>