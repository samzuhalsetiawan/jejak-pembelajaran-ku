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
        $str = "{$this->judul} | {$this->penulis}, {$this->penerbit}, ({$this->harga})";
        return $str;
    }

}

class Komik extends Produk {
    public $jmlHalaman;

    public function __construct($judul, $penulis, $penerbit, $harga, $jmlHal) {
        parent::__construct($judul, $penulis, $penerbit, $harga);
        $this->jmlHalaman = $jmlHal;
    }

    public function getLabel() {
        $str = parent::getLabel() . " {$this->jmlHalaman} - halaman";
        return $str;
    }
}

class Game extends Produk {
    public $jamMain;

    public function __construct($judul, $penulis, $penerbit, $harga, $jamMain) {
        parent::__construct($judul, $penulis, $penerbit, $harga);
        $this->jamMain = $jamMain;
    }

    public function getLabel() {
        return parent::getLabel() . " {$this->jamMain} - jam";
    }

}

class Cetak {
    public function cetak( Produk $produk) {
        return "{$produk->getLabel()}, {$produk->penerbit}, {$produk->harga}";
    }
}

$naruto = new Komik('naruto', 'masashi kisimoto', 'shonen jump', 30000, 100);
$uncharted = new Game('uncharted', 'neil drunkman', 'sony pictures', 250000, 30);

echo "{$naruto->getLabel()} <br>";
echo "{$uncharted->getLabel()}";