<?php

class Produk {
    public  $judul,
            $penulis,
            $penerbit,
            $harga,
            $jmlHalaman,
            $waktuMain,
            $tipe;

    public function __construct($judul, $penulis, $penerbit, $harga, $jmlHal, $waktu, $tipe) {
        $this->judul = $judul;
        $this->penulis = $penulis;
        $this->penerbit = $penerbit;
        $this->harga = $harga;
        $this->jmlHalaman = $jmlHal;
        $this->waktuMain = $waktu;
        $this->tipe = $tipe;
    }

    public function getLabel() {
        $str = "{$this->judul} | {$this->penulis}, {$this->penerbit}, ({$this->harga})";
        if ($this->tipe == "komik") {
            $str .= " - {$this->jmlHalaman} halaman";
        } else {
            $str .= " - {$this->waktuMain} jam";
        }
        return $str;
    }

}

class Cetak {
    public function cetak( Produk $produk) {
        return "{$produk->getLabel()}, {$produk->penerbit}, {$produk->harga}";
    }
}

$naruto = new Produk('naruto', 'masashi kisimoto', 'shonen jump', 30000, 100, 0, "komik");
$uncharted = new Produk('uncharted', 'neil drunkman', 'sony pictures', 250000, 0, 50, "game");
echo $naruto->getLabel();