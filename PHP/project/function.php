<?php 
	$con = mysqli_connect("localhost", "root", "", "project");
	//sebuah kunci akses login tambahan
	$akses = false;
	//fungsi untuk memasukan nama user ke database untuk ditampilkan di page selanjutnya
	function userLogin ($data) {
		global $con;
		$user = htmlspecialchars($data["username"]);
		$update = "UPDATE login SET nama_user = '$user', online = 1 where no = 1";
		mysqli_query($con, $update);
	}
	//fungsi untuk mencegah user memaksa masuk melalui link
	function webSecurity () {
		global $login;
		if ($login["online"] == 1 || $_GET["akses"] == true) {
			backOffline(); //meng-0 kan kembali nilai online pada database login
			unset($_GET["akses"]);
		} else {
			header("Location: index.php");
			exit;
		}
	}
	//fungsi untuk mengambil kembali nama user yg telah disimpan pada login page
	$login = [];
	function ambilDataLogin () {
		global $con;
		global $login;
		$query = mysqli_query($con, "SELECT * FROM login");
		$result =  mysqli_fetch_assoc($query);
		$login = $result;
	}
	//fungsi untuk meng-0 kan kembali value online pada database login
	function backOffline () {
		global $con;
		$update = "UPDATE login SET online = 0 where no = 1";
		mysqli_query($con, $update);
	}
	//fungsi untuk mengambil data pada database merk
	$laptop = [];
	function ambilDataMerk () {
		global $con;
		global $laptop;
		$query = mysqli_query($con, "SELECT * FROM merk");
		while ($merk = mysqli_fetch_assoc($query)) {
		$laptop [] = $merk;
		}
	}
	//fungsi untuk menambah data baru kedalam tabel merk
	function tambahDataMerk($data) {
		global $con;
		$nomor = 1;
		$query = mysqli_query($con, "SELECT * FROM merk");
		while ($merk = mysqli_fetch_assoc($query)) {
			$nomor++;
		}
		$merk_laptop = $data["brand_laptop"];
		$jumlah = $data["jumlah_produk"];
		$logo = $merk_laptop.".png";
		$query = "INSERT INTO merk VALUES($nomor,'$merk_laptop', $jumlah,'$logo')";
		mysqli_query($con, $query);
	}
	//fungsi untuk menghapus data dalam tabel merk
	function hapusDataMerk($data) {
		global $con;
		mysqli_query($con, "DELETE FROM merk WHERE brand_laptop = '$data'");
	}

	//fungsi untuk memilih row/record
	function selectData($tabel, $field, $data) {
		global $con;
		$query = "SELECT * FROM $tabel WHERE $field = '$data'";
		 return mysqli_query($con, $query);
	}
?>