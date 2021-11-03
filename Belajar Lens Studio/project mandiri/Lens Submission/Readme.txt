Nama Lens: Pesawat dihidung

lens ini akan menampilkan sebuah pesawat yang mengikuti posisi hidung, dan ketika mulut dibuka pesawat tersebut akan menembak
(walaupun tidak keluar peluru hehe xD)

Karya Original

Untuk project submission ini saya telah menerapkan 4 materi yang telah diajarkan kedalam 1 lens
1. Post Effect
	Akan ada Post Effect Heat pada lens ketika mulut dibuka.

2. Segmentation
	Background lens menggunakan Potrait Background Segmentation

3. Face Effect
	Gambar pesawat diletakan dihidung dengan menggunakan script PinToLandmarks.
	ini merupakan penerapan dari modul Face Effect.

4. Visual scripting
	Saya telah membuat script graph dengan logika sebagai berikut:
	Ketika lens dimulai -> munculkan teks "Buka mulut untuk menembak" -> disable Post Effect (event ini hanya ditriger sekali)
	Ketika Mulut dibuka -> hilangkan teks "Buka mulut untuk menembak" -> eneble Post Effect
	Ketika Mulut ditutup -> disable Post Effect
	
	Pada Script Behavior:
		Ketika Mulut dibuka -> Ubah tekstur object pesawat (fly.png) menjadi tekstur menembak (shoot.png)
		Ketika Mulut dibuka -> Ubah tekstur object pesawat (shoot.png) menjadi tekstur idle (fly.png)

Demikian deskripsi lengkap mengenai lens sederhana yang saya buat
Walaupun sederhana setidaknya lens ini sudah menerapkan semua modul yang telah saya pelajari
Terima kasih tim Dicoding :)