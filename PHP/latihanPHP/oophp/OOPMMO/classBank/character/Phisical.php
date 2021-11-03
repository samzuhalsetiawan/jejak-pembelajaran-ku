<?php

class Phisical extends Character {
    protected $phisicalDemage,
                $phisicalPenet;

    public function __construct(
        $namaJob,
        $HP,
        $phisicalDef,
        $magicalDef,
        $HPRegen,
        $phisicalDemage,
        $phisicalPenet) {
            parent::__construct(
                $namaJob,
                $HP,
                $phisicalDef,
                $magicalDef,
                $HPRegen);
            $this->phisicalDemage = $phisicalDemage;
            $this->phisicalPenet = $phisicalPenet;
        }
    
    public function getInfo(){
        $this->getInfoChar();
    }
}