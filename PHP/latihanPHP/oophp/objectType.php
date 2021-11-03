<?php

class Produk {
    public  $judul,
            $penulis,
            $penerbit,
            $harga;

    public function __construct($judul, $penulis, $penerbit, $harga) {
        $this->judul = $judul;
        $this->penulis = $penulis;
        $this->penerbit = $penerbit;
        $this->harga = $harga;

    }

    public function getLabel() {
        return "$this->judul, $this->penulis";
    }
}

class Cetak {
    public function cetak( Produk $produk) {
        return "{$produk->getLabel()}, {$produk->penerbit}, {$produk->harga}";
    }
}

$naruto = new Produk("naruto", "masashi kisimoto", "shonen jump", 30000);
$uncharted = new Produk("uncharted", "Neil Drunkman", "sony computer", 250000);

echo "komik : " . $naruto->getLabel();
echo "<br>";
echo "game : " . $uncharted->getLabel();
echo "<br>";
$cetaksaya = new Cetak();
echo $cetaksaya->cetak($naruto);