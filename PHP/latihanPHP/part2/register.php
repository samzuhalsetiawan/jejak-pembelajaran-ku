<?php  
	$conn = mysqli_connect("localhost", "root", "", "project");
	if (isset($_POST["submit"])) {
		$error = $_FILES["gambar"]["error"];
		if ($error === 4) {
			echo "<script>
					alert('Foto tidak boleh kosong!');
				</script>";
		}else {
			//cek username
			$username = htmlspecialchars(strtolower(stripslashes($_POST["username"])));
			$query = "SELECT username FROM user WHERE username = '$username'";
			$result = mysqli_query($conn, $query);
			if (mysqli_fetch_assoc($result)) {
				echo "<script>
						alert('username sudah ada!');
					</script>";
			} else {
				$password = mysqli_real_escape_string($conn, $_POST['password']);
				$konfirmasi = mysqli_real_escape_string($conn, $_POST["konfirmasi"]);
				if ($password !== $konfirmasi) {
					echo "<script>
							alert('Konfirmasi password tidak sesuai!');
						</script>";
				}else {
					$password2 = password_hash($password, PASSWORD_DEFAULT);
					$gambar = $_FILES["gambar"]["name"];
					$extensi = explode(".", "$gambar");
					$extensiGambar = strtolower(end($extensi));
					$allowedExtensi = ["jpg", "jpeg", "png"];
					$cek = in_array($extensiGambar, $allowedExtensi);
					if (!$cek) {
						echo "<script>
								alert('Format gambar tidak diperbolehkan!');
							</script>";
					}else {
						$ukuran = $_FILES["gambar"]["size"];
						if ($ukuran > 1500000) {
							echo "<script>
									alert('Ukuran gambar terlalu besar!');
								</script>";
						}else {
							$status = htmlspecialchars($_POST["status"]);
							$gambarFix = uniqid();
							$gambarFix .= ".";
							$gambarFix .= "$extensiGambar";
							$insert = "INSERT INTO user VALUES (
										'',
										'$username',
										'$password2',
										'$status',
										'$gambarFix'
									)";
							$result = mysqli_query($conn, $insert);
							if (mysqli_affected_rows($conn) > 0) {
								$tmp = $_FILES["gambar"]["tmp_name"];
								move_uploaded_file($tmp, "img/".$gambarFix);
								echo "<script>
										alert('Data berhasil ditambahkan!');
									</script>";
							}else {
								echo "<script>
										alert('Data gagal ditambahkan!');
									</script>";
							}
						}
					}
				}
			}
		}
	}
?>

<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title>Registrasi</title>
	<style>
		label {
			display: block;
		}
	</style>
</head>
<body>
	<h1>Registrasi Akun</h1>
	<form action='' method='post' enctype="multipart/form-data">
	<ul>
		<li>
			<label for="username">Username: </label>
			<input type="text" name="username" id="username" required>
		</li>
		<li>
			<label for="status">Job: </label>
			<input type="text" name="status" id="status" required>
		</li>
		<li>
			<label for="password">Password: </label>
			<input type="password" name='password' id="password" required>
		</li>
		<li>
			<label for="konfirmasi">Konfirmasi Password: </label>
			<input type="password" name="konfirmasi" id="konfirmasi">
		</li>
		<li>
			<label for="gambar">Upload Foto: </label>
			<input type="file" name="gambar" id="gambar">
		</li>
		<p><button type="submit" name="submit">Daftar Akun</button> daftar disini</p>
	</ul>
	</form>
	<form action="login.php" method="get">
	<ul>
		<p>Sudah punya akun? <button type="submit" name="submit">Masuk akun</button></p>
	</ul>
	</form>
</body>
</html>