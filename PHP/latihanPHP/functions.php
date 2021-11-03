<?php 
// database.php
	$db = mysqli_connect("localhost", "root", "", "db_mahasiswa");
	$result = mysqli_query($db, "SELECT * FROM mahasiswa");

	while ($data = mysqli_fetch_assoc($result)) {
		$mahasiswa[] = $data;
	}

// edit.php
	function tambah ($data) {
		global $db;
		$nama = htmlspecialchars($data["nama_mahasiswa"]);
		$NIM = htmlspecialchars($data["NIM"]);
		$NIMint = (int)$NIM;
		$jk = htmlspecialchars($data["jenis_kelamin"]);
		$prodi = htmlspecialchars($data["prodi"]);
		$alamat = htmlspecialchars($data["alamat"]);
		$gambar = htmlspecialchars($data["gambar"]);

		$query = "INSERT INTO mahasiswa VALUES ('', $NIMint,'$nama','$jk','$prodi','$alamat','$gambar')";
		mysqli_query($db, $query);
		
		return mysqli_affected_rows($db);
	}
?>