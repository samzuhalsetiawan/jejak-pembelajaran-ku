<?php

class Laptop {
    public  $nama,
            $merk,
            $cpu,
            $gpu,
            $vga,
            $ram,
            $storage,
            $display;

    public function __construct($nama, $merk, $cpu, $gpu, $vga, $ram, $storage, $display) {
        $this->nama = $nama;
        $this->merk = $merk;
        $this->cpu = $cpu;
        $this->gpu = $gpu;
        $this->vga = $vga;
        $this->ram = $ram;
        $this->storage = $storage;
        $this->display = $display;
    }
}

$acer = new Laptop("Acer Nitro 5", "Acer", "intel core i7 9900H", "intel iris graphics", "Nvidia GTX 1660 Ti", "8 Gb 2 slot", "SSD 500 Gb", "IPS 144 Hz");
$asus = new Laptop("Asus TUF 5500 GD", "Asus", "intel core i7 9900H", "intel iris graphics", "Nvidia GTX 1660 Ti", "8 Gb 2 slot", "SSD 500 Gb", "IPS 144 Hz");

echo "{$acer->merk} | {$acer->nama} <br>";
echo "Specification: <br>";
echo "Processor : {$acer->cpu}, {$acer->ram}<br>";
echo "Graphics : {$acer->gpu}, {$acer->vga} <br>";
echo "Storage : {$acer->storage} <br>";
echo "Display : {$acer->display} <br>";
echo "<br> <hr> <br>";
echo "{$asus->merk} | {$asus->nama} <br>";
echo "Specification: <br>";
echo "Processor : {$asus->cpu}, {$asus->ram}<br>";
echo "Graphics : {$asus->gpu}, {$asus->vga} <br>";
echo "Storage : {$asus->storage} <br>";
echo "Display : {$asus->display} <br>";
echo "<br> <hr> <br>";
echo "{$acer->merk} | {$acer->nama} <br>";
echo "Specification: <br>";
echo "Processor : {$acer->cpu}, {$acer->ram}<br>";
echo "Graphics : {$acer->gpu}, {$acer->vga} <br>";
echo "Storage : {$acer->storage} <br>";
echo "Display : {$acer->display} <br>";