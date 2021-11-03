<?php
	if (isset($_POST["username"]) && isset($_POST["password"])) { 
		if ($_POST["username"] == "Sam Zuhal" && $_POST["password"] == "rahasia") {
			header("Location: landing.php");
			exit;
		}else {
			$error = true;
		}
	}
?>

<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title>Login Pages</title>
	<style type="text/css">
		.login {
			width: 400px;
			height: 280px;
			background-color: #DCDCDC;
		}
	</style>
</head>
<body>
	<div class="login">
	<h1>Login!</h1>
	<?php if (isset($error)) : ?>
		<p style="color: red; font-style: italic;"> Username / Pasword salah!</p>
	<?php endif; ?>
	<form action="" method="post">
		<ul>
			<li>
				<label for="login"> Username : </label>
				<input type="text" name="username" id="login">
			</li>
			<li>
				<label for="pass"> Password : </label>
				<input type="password" name="password" id="pass">
			</li>
			<button type="submit" name="submit">Login</button>
		</ul>

	</form>
	</div>
</body>
</html>