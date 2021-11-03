<?php

class Produk {
    public  $judul = "uncharted",
            $penulis = "penulis",
            $penerbit,
            $harga;

    public function getLabel() {
        return "$this->penulis, $this->penerbit";
    }

}

// $produk1 = new Produk();
// $produk1->judul = "naruto";
// var_dump($produk1);

// $produk2 = new Produk();
// $produk2->publisher = "entahlah";
// var_dump($produk2);

$naruto = new Produk();
$naruto->judul = "naruto";
$naruto->penulis = "masashi kisimoto";
$naruto->penerbit = "shonen jump";
$naruto->harga = 30000;

echo "komik : $naruto->judul, $naruto->penulis";
echo "<br>";
echo $naruto->getLabel();

echo "<hr>";

$uncharted = new Produk();
$uncharted->judul = "uncharted";
$uncharted->penulis = "Neil Druckmann";
$uncharted->penerbit = "Sony Computer";
$uncharted->harga = 250000;

echo "game : " .$uncharted->getLabel();