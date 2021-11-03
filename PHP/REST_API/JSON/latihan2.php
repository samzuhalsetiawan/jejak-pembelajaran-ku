<?php

    $data = file_get_contents("coba.json");
    $pembeli = json_decode($data, true);

    var_dump($pembeli);

?>