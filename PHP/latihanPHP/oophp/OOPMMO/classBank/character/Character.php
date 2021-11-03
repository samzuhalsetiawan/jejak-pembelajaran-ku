<?php

abstract class Character {

    protected   $namaJob,
                $HP,
                $phisicalDef,
                $magicalDef,
                $HPRegen;

    public function __construct(
        $namaJob,
        $HP,
        $phisicalDef,
        $magicalDef,
        $HPRegen) {
            $this->namaJob = $namaJob;
            $this->HP = $HP;
            $this->phisicalDef = $phisicalDef;
            $this->magicalDef = $magicalDef;
            $this->HPRegen = $HPRegen;
        }

    public function tambahItems($items) {
        foreach($items as $prop => $value) {
            if (property_exists($this, $prop)) {
                $this->$prop += $value;
            }
        }

    }

    abstract public function getInfo();

    public function getInfoChar () {
        foreach ($this as $key => $value) {
            print "$key => $value<br>";
        }
    }
}
