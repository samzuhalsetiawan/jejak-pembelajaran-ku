<?php 
	require 'function.php';
// mengecek apakah password dan username benar
	if (isset($_POST["submit"])) {
		if ($_POST["username"] == "SamZ03" && $_POST["password"] == "W103v66t") {
			userLogin($_POST); //jika benar kirim data nama user
			echo "
					<script>
						alert('Login Berhasil !');
						document.location.href='landingPage.php';
					</script>
				";
		}else {
			echo "
					<script>
						alert('username / password salah !');
					</script>
				";
		}
	}
?>

<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title>PT Selalu Jaya</title>
</head>
<body>
	<h1 align="center">Selamat datang di PT Selalu Jaya</h1>
	<p align="center">Silahkan login untuk masuk kedalam database</p>
	<form action="" method="post">
		<div align="center">
			<label for="username">Username :</label><br>
			<input type="text" name="username" id="username" required="">
		</div>
		<div align="center">
			<label for="password">Password : </label><br>
			<input type="password" name="password" id="password" required="">
		</div>
		<br>
		<div align="center">
		<button type="submit" name="submit">Login</button>
		</div>
	</form>
</body>
</html>