<?php 
//ini gunanya untuk menghubungkan script php ini dengan database
//	$db = mysqli_connect("localhost", "root", "", "db_mahasiswa");
	//ini gunanya untuk memilih tabel dan menangkap data dalam database tersebut dan disimpan dalam variabel result
//	$result = mysqli_query($db, "SELECT * FROM mahasiswa");
	// data yang telah ditangkap harus diubah dalam bentuk array asosiatif dengan syntax
	// mysqli_fetch_assoc
	// itupun data yang tertangkap hanya 1 array, maka diperlukan loop untuk mengambil semua data array
	// kemudian disimpan dalam variabel mahasiswa
//	while ($data = mysqli_fetch_assoc($result)) {
		//kenapa pakai = ? karena "=" dalam php itu berarti menugaskan data yg ada dalam operand kanan
		//untuk berpindah ke data yang ada di operand sebelah kiri "="
		//dalam looping ini berarti while akan terus bekerja sampai mysqli telah memasukan semua datanya ke
		//dalam variable $data
//		$mahasiswa[] = $data;
//	}
require 'functions.php';

?>


<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title>Mahasiswa</title>
</head>
<body>
	<h1>Data-data mahasiswa</h1>

	<p><a href="edit.php">Tambah data mahasiswa</a></p>
	<table border="1" ; cellpadding="5"; cellspacing="0">
		<tr>
			<th>No</th>
			<th>NIM</th>
			<th>Nama Mahasiswa</th>
			<th>Gambar</th>
			<th>Jenis Kelamin</th>
			<th>Prodi</th>
			<th>Alamat</th>
		</tr>
		<?php $nomor = 1; ?>
		<?php foreach($mahasiswa as $mhs) : ?>
		<tr>
			<td><?= $nomor; ?></td>
			<td><?= $mhs["NIM"]; ?></td>
			<td><?= $mhs["nama_mahasiswa"]; ?></td>
			<td><img src="img2/<?= $mhs["gambar"]; ?>"; width = "50"></td>
			<td><?= $mhs["jenis_kelamin"]; ?></td>
			<td><?= $mhs["prodi"]; ?></td>
			<td><?= $mhs["alamat"]; ?></td>
		</tr>
		<?php $nomor++; ?>
		<?php endforeach; ?>
	</table>

</body>
</html>