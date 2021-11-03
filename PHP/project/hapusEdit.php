<?php 
	require 'function.php';
	ambilDataLogin();
	webSecurity();

	if ($_GET["aksi"] == 2) {
		hapusDataMerk($_GET["nama"]);
		$akses = true;
		echo "
			<script>
				alert('data berhasil dihapus!');
				document.location.href='tambahMerk.php?akses=$akses';
			</script>
		";
	} else { 
		$result = selectData("merk", "brand_laptop", $_GET["nama"]);
		$baru[] = mysqli_fetch_assoc($result);
	?>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title>Edit data</title>
</head>
<body>
	<h1>Edit data</h1>
	<form action="tambah.php" method="post">
	<ul>
			<input type="hidden" name="no" value="<?=$baru[0]['no'];?>">
		<li>
			<label for="brand_laptop">Nama Merk: </label>
			<input type="text" name="brand_laptop" id="brand_laptop" 
					value="<?=$baru[0]['brand_laptop'];?>" required>
		</li>
		<li>
			<label for="jumlah_produk">Jumlah Unit: </label>
			<input type="number" name="jumlah_produk" id="jumlah_produk" 
					value="<?=$baru[0]['jumlah_produk'];?>" required>
		</li>
			<input type="hidden" name="logo" value="<?=$baru[0]['logo'];?>">
			<br>
		<button type="submit" name="submit" onclick="return confirm('yakin ingin mengubah?')" >
			Ubah Data
		</button>
	</ul>
	</form>

</body>
</html>

<?php } ?>