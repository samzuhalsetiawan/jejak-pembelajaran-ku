<?php
	session_start();
	$conn = mysqli_connect("localhost", "root", "", "project");
	if (isset($_COOKIE["user"])) {
		if ($_COOKIE["user"] == "online") {
			$_SESSION["login"] = "berhasil";
		}
	}
	if (isset($_SESSION["login"])) {
		header("Location: halamanUtama.php");
		exit;
	}
	if (isset($_POST["submit"])) {
		$username = strtolower($_POST['username']);
		$password = $_POST['password'];
		$query = "SELECT * FROM user WHERE username ='$username'";
		$result = mysqli_query($conn, $query);

		if (mysqli_num_rows($result) === 1) {
			$hash = mysqli_fetch_assoc($result);
			$hasil = password_verify($password, $hash["password"]);
			if ( $hasil == true ) {	
				$_SESSION["login"] = "berhasil";
				if (isset($_POST["ingat"])) {
					setcookie('user', 'online', time() + 86400);
				}
				echo "<script>
						alert('data benar');
					</script>";
				header("Location: halamanUtama.php");
				exit;
			}else{
				echo "<script>
						alert('Password yang anda masukan salah!');
					</script>";
			}
		} else {
			echo "<script>
					alert('Username tidak ditemukan!');
				</script>";
		}
	}
?>

<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title>Halaman Login</title>
	<style>
		.cek {
			display: block;
		}
	</style>
</head>
<body>
	<h1 align="center">Halaman Login</h1>
	<form action='' method='post'>
		<ul>
			<div align="center">
				<label class="cek" for="username">Username: </label>
				<input type="text" name="username" id="username" placeholder="Username Anda..."
				 autocomplete="off" autofocus><br><br>
			 </div>
			 <div align="center">
				<label class="cek" for="password">Password: </label>
				<input type='password' name='password' id="password" placeholder="Password Anda..."><br><br>
			</div>
			<div align="center">
				<input type="checkbox" name="ingat" id="ingat">
				<label for="ingat">Ingat saya!</label><br><br>
			</div>
			<div align="center">
				<button type="submit" name="submit">Masuk!</button>
			</div>
		</ul>
	</form>
	<form action="register.php" method="get">
		<ul>
			<div align="center">
				<p>belum punya akun? </p>
				<button type="submit" name="submit">Daftar disini!</button>
			</div>
		</ul>
	</form>
</body>
</html>