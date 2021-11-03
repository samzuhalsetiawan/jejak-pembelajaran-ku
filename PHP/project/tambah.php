<?php 
	require 'function.php';
	$akses = true;

	$no = $_POST['no'];
	$nama = $_POST['brand_laptop'];
	$jumlah = $_POST['jumlah_produk'];
	$jumlahInt = (int)$jumlah;
	$logo = $_POST['brand_laptop'].".png";

	mysqli_query($con, "UPDATE merk SET brand_laptop = '$nama' where no = $no");
	mysqli_query($con, "UPDATE merk SET jumlah_produk = $jumlahInt where no = $no");
	mysqli_query($con, "UPDATE merk SET logo = '$logo' where no = $no");

	echo "
			<script>
				alert('data berhasil diubah!');
				document.location.href='tambahMerk.php?akses=$akses';
			</script>
		";
?>