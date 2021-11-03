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

    public function getlabel() {
        return "$this->judul, $this->penulis";
    }
}

$naruto = new Produk("naruto", "masashi kisimoto", "shonen jump", 30000);
$uncharted = new Produk("uncharted", "Neil Drunkman", "sony computer", 250000);

echo "komik : " . $naruto->getLabel();
echo "<br>";
echo "game : " . $uncharted->getLabel();