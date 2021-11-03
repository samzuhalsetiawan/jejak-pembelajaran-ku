<?php
	$conn = mysqli_connect("localhost", "root", "", "project");
	$kunci = $_GET["kunci"];
	$query = "SELECT * FROM user WHERE 
				username LIKE '%$kunci%' OR
				status LIKE '%$kunci%'
			";
	$result = mysqli_query($conn, $query);
	while ($data = mysqli_fetch_assoc($result)) {
			$user[] = $data;
	}
?>
<?php if (mysqli_num_rows($result) > 0) : ?>
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
<?php else : ?>
	<h1>Data tidak ditemukan!</h1>
<?php endif; ?>