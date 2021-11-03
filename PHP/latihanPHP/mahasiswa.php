<?php 

$mahasiswa = [
	[
		"nama" => "Sam Zuhal Setiawan",
		"NIM" => 2005176043,
		"jurusan" => "Pendidikan Komputer",
		"jenis_kelamin" => "L",
		"foto" => "gambar1.jpg"
	],
	[
		"nama" => "Ardiyanto",
		"NIM" => 2005176003,
		"jurusan" => "Pendidikan Komputer",
		"jenis_kelamin" => "L",
		"foto" => "gambar2.jpg"
	],
	[
		"nama" => "Bagas Gunawan",
		"NIM" => 2005176005,
		"jurusan" => "Pendidikan Komputer",
		"jenis_kelamin" => "L",
		"foto" => "gambar3.jpg"
	]
];

?>



<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title>database mahasiswa</title>
	<style type="text/css">
		.bgData {
			width: 280px;
			height: 200px;
			padding: 30px;
			margin: 5px;
			background-color: #DCDCDC;
			float: left;
		}
	</style>
</head>
<body>
	<h1>Data Mahasiswa</h1>
	<?php foreach ($mahasiswa as $mhs) : ?>
		<ul>
			<div class="bgData">
			<img src="img/<?= $mhs["foto"]; ?>">
			<br><br>
			<li>
				<label for="link">
				Nama Mahasiswa : 
				</label>
				<a href="dataMahasiswa.php?nama=<?= $mhs["nama"]; ?>&gambar=<?= $mhs["foto"]; ?>">
					<?= $mhs["nama"]; ?>		
				</a>
			</li>
			<li>NIM : <?= $mhs["NIM"]; ?></li>
			<li>Jurusan : <?= $mhs["jurusan"]; ?></li>
			<li>Jenis Kelamin : <?= $mhs["jenis_kelamin"]; ?></li>
			</div>
		</ul>
	<?php endforeach; ?>
</body>
</html>