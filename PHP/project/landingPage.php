<?php
	require 'function.php';

	ambilDataLogin();	
	webSecurity();
	ambilDataMerk();
	$akses = true;

?>

<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title>Dashboard</title>
	<style type="text/css">
		.laptop {
			width: 190px;
			height: 200px;
			padding: 30px;
			background-color: #DCDCDC;
			margin: 5px;
			float: left;
			border: 3px solid green;
		}
		.nama {
			text-align: center;
		}
	</style>
</head>
<body>
	<div align="center">
		<h1>Selamat datang <label style="color: blue;"><?= $login["nama_user"]; ?> ! </label></h1>
	</div>
	<a href="index.php">Kembali ke halaman login</a><br><br>
	<p align="center"><strong>Berikut daftar merk unit yang tersedia</strong></p>
	<?php foreach ($laptop as $merk) : ?>
		<div class="laptop">
			<div align="center">
				<img src="img/merk/<?= $merk['logo']; ?>">
			</div>
			<br>
			<div class="nama">
				<h3><a href=""><?= $merk["brand_laptop"]; ?></a></h3>
			</div>
			<div align="center">
				<?= $merk["jumlah_produk"]; ?> unit
			</div>
		</div>
	<?php endforeach;?>
	<div class="laptop">
		<h3 align="center"><a href="tambahMerk.php?akses=<?=$akses;?>">Tambahkan merk baru!</a></h3>
	</div>
</body>
</html>