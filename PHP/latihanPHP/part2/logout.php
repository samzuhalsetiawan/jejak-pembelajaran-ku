<?php 
	session_start();
	$_SESSION = [];
	session_unset();
	session_destroy();
	setcookie('user', 'online', time() - 3600);

	header("Location: login.php");
?>