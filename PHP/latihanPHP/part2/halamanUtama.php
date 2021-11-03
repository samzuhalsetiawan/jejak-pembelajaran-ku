<?php
	session_start();
	if (isset($_COOKIE["user"])) {
		if ($_COOKIE["user"] == "online") {
			$_SESSION["login"] = "berhasil";
		}
	}
	if (!isset($_SESSION["login"])) {
		header("Location: login.php");
		exit;
	} 
	$conn = mysqli_connect("localhost", "root", "", "project");
	function data($query) {
		global $conn;
		global $user;
		$result = mysqli_query($conn, $query);
		while ($data = mysqli_fetch_assoc($result)) {
		$user[] = $data;
		}
	}
	if (isset($_POST["submit"])) {
		$keyword = $_POST["cari"];
		$query = "SELECT * FROM user WHERE 
					username LIKE '%$keyword%' OR
					status LIKE '%$keyword%'
				";
		data($query);
	} else {
		$query = "SELECT * FROM user ";
		data($query);
	}
?>

<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title>Selamat datang!</title>
	<style>
		#tombol {
			display: none;
		}
		.judul {
			background-color: yellow;
		}
		#tabel {
			background-color: #64C8E0;
		}
		.coba {
			display: inline-block;
			width: 50%;
			background-color: #E75F4F;
		}
	</style>
</head>
<body>
	<a href="logout.php">Log Out!</a>
	<div class="judul">
		<h1> Data-data akun user: </h1>
	</div>
	<div class="coba">
	<form action="" method="post">
		<input type="text" name="cari" size="50" placeholder="Cari data user..." 
		autocomplete="off" autofocus id="cari">
		<button type="submit" name="submit" id="tombol">Cari!</button>
	</form>
	</div>
	<div class="coba">
		<a href="login.php">kembali ke halaman login</a>
	</div>
	<div id="tabel">
	<table border="1" cellpadding="3" cellspacing="0">
		<tr>
			<th>No</th>
			<th>Username</th>
			<th>Potition</th>
			<th>Foto User</th>
		</tr>
		<?php $nomor = 1; ?>
		<?php foreach ($user as $u) : ?>
		<tr>
			<td><?= $nomor; ?></td>
			<td><?= $u["username"]; ?></td>
			<td><?= $u["status"]; ?></td>
			<td><img width="50" src="img/<?=$u['gambar']; ?>"></td>
		</tr>
		<?php $nomor++; ?>
		<?php endforeach; ?>
	</table>
	</div>
<script src="script.js"></script>
</body>
</html>