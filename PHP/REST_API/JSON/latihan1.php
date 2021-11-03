<?php

$dbh = new PDO("mysql:host=localhost;dbname=toko_online", "root", "");
$db = $dbh->prepare("SELECT * FROM pembeli");
$db->execute();
$pembeli = $db->fetchAll(PDO::FETCH_ASSOC);

$data = json_encode($pembeli);

var_dump($data);

?>