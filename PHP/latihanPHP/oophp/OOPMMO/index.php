<?php

require_once __DIR__ . '/classBank/innit.php';

// Char    $namaJob,
        // $HP,
        // $phisicalDef,
        // $magicalDef,
        // $HPRegen,
        // $phisicalDemage,
        // $phisicalPenet

//items
    // $phisicalDemage,
    // $phisicalPenet,
    // $critChance,
    // $magicalDemage,
    // $magicalPenet,
    // $phisicalDef,
    // $magicalDef,
    // $HP,
    // $HPRegen

$swordsman = new Phisical("Swordsman", 1000, 5, 5, 10, 30, 10);
$bladeOfDespair = new Items(75, 10, 0, 0, 0, 0, 0, 0, 0);
$queensWing = new Items(10, 0, 0, 0, 0, 0, 0, 100, 0);

$swordsman->tambahItems($bladeOfDespair->getInfoItems());
$swordsman->tambahItems($queensWing->getInfoItems());
echo $swordsman->getInfoChar();
echo "<br><br>";
var_dump($bladeOfDespair->getInfoItems());
echo "<br><br>";
var_dump($queensWing->getInfoItems());