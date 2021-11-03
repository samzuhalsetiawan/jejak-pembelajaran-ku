<?php 
	require 'functions.php';
// document.location.href = 'database.php';
	if (isset($_POST["submit"])) {
		tambah($_POST);
			echo tambah($_POST);
			echo 	"
					<script>
						alert('Data berhasil dimasukan!');
								
					</script>
			";
	}
?>

<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title>Enter data</title>
</head>
<body>
	<h1>Silahkan masukan data yang ingin di input!</h1>
	<form action="" method="post">
	<ul>
		<li>
			<label for="nama_mahasiswa">Nama Mahasiswa:</label>
			<input type="text" name="nama_mahasiswa" id="nama_mahasiswa" required>
		</li>
		<li>
			<label for="NIM">NIM :</label>
			<input type="text" name="NIM" id="NIM" required>
		</li>
		<li>
			<label for="jenis_kelamin">Jenis Kelamin :</label>
			<input type="text" name="jenis_kelamin" id="jenis_kelamin" required>
		</li>
		<li>
			<label for="prodi">Prodi :</label>
			<input type="text" name="prodi" id="prodi" required>
		</li>
		<li>
			<label for="alamat">Alamat :</label>
			<input type="text" name="alamat" id="alamat" required>
		</li>
		<li>
			<label for="gambar">Foto :</label>
			<input type="text" name="gambar" id="gambar" required>
		</li>
		<button type="submit" name="submit">Tambahkan data</button> | 
	</ul>
	</form>
	<button type="submit" name="exit"><a href="database.php">Kembali</a></button>
</body>
</html>