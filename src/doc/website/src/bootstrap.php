<?php
/**
 * Created by PhpStorm.
 * User: niklas
 * Date: 04.09.18
 * Time: 15:29
 */
namespace App;

use Niklas\DAO;

ini_set("display_errors",1);
require_once __DIR__ . "/../vendor/autoload.php";
require_once __DIR__ . "/../resources/Config.php";
DAO::Init(new \Niklas\DAO(Config::$HOST, Config::$USER, CONFIG::$PASS, CONFIG::$DB));