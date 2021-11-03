<?php 
	require 'function.php';
	$akses = true;
	ambilDataLogin();
	webSecurity();
	if (isset($_POST["submit"])) {
		tambahDataMerk($_POST);
		echo "
				<script>
					alert('data berhasil ditambahkan!');
				</script>
			";
	}
	ambilDataMerk();

?>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title>Edit data</title>
	<style type="text/css">
		.tabel {
			padding-right: 90px;
			padding-left: 30px;
			float: left;
		}
	</style>
</head>
<body>
	<h1 align="center">Tambahkan atau Hapus merk</h1>
	<p><a href="landingPage.php?akses=<?=$akses;?>">kembali ke  dashboard</a></p>
	<div class="tabel">
	<table border="1" cellpadding="3" cellspacing="0">
		<tr>
			<th>No</th>
			<th>Nama Merk</th>
			<th>Logo</th>
			<th>Jumlah Unit</th>
			<th>Edit | Hapus</th>
		</tr>
		<?php $nomor = 1; ?>
		<?php foreach ($laptop as $merk) : ?>
			<tr>
				<td><?= $nomor; ?></td>
				<td><?= $merk["brand_laptop"]; ?></td>
				<td align="center"><img src="img/merk/<?= $merk["logo"]; ?>"></td>
				<td align="center"><?= $merk["jumlah_produk"]; ?></td>
				<!-- GET mengirimkan 2 value, nama dan aksi. aksi = 1 untuk edit,
					atau aksi = 2 untuk hapus -->
				<td align="center">
					<a href="hapusEdit.php?nama=<?=$merk['brand_laptop'];?>&aksi=1&akses=$akses">
						Edit
					</a> | 
					<a onclick="return confirm('apakah anda ingin menghapus data ini?')" 
						href="hapusEdit.php?nama=<?=$merk['brand_laptop'];?>&aksi=2&akses=$akses">
						Hapus
					</a>
				</td>
			</tr>
		<?php $nomor++; ?>
		<?php endforeach; ?>
	</table>
	</div>
	<div class="tabel">
		<form action="" method="post">
		<ul>
			<h3>Masukan Merk Laptop Baru</h3>
			<li>
				<label for="brand_laptop">Nama Merk: </label>
				<input type="text" name="brand_laptop" id="brand_laptop">
				<br><br>
			</li>
			<li>
				<label for="jumlah_produk">Jumlah Unit: </label>
				<input type="number" name="jumlah_produk" id="jumlah_produk">
				<br><br>
			</li>
			<button type="submit" name="submit" onclick="return confirm('tambahkan data?')">
				Tambahkan merk
			</button>
		</ul>
		</form>
	</div>
</body>
</html>