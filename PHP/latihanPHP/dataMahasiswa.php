<?php 

if (!isset($_GET["nama"]) || !isset($_GET["gambar"])) {
	header("Location: mahasiswa.php");
	exit;
}

?>



<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title><?= $_GET["nama"]; ?></title>
</head>
<body>
	<br><br>
	<h1 align="center">Selamat datang, <?= $_GET["nama"]; ?> !</h1>
	<br>
	<p align="center"><img src="img/<?= $_GET["gambar"]; ?>"></p>

</body>
</html>