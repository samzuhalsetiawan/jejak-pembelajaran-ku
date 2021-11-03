<?php

class Items {
    protected  $phisicalDemage,
            $phisicalPenet,
            $critChance,
            $magicalDemage,
            $magicalPenet,
            $phisicalDef,
            $magicalDef,
            $HP,
            $HPRegen;
    
    public function __construct(
        $phisicalDemage,
        $phisicalPenet,
        $critChance,
        $magicalDemage,
        $magicalPenet,
        $phisicalDef,
        $magicalDef,
        $HP,
        $HPRegen) {
            $this->phisicalDemage = $phisicalDemage;
            $this->phisicalPenet = $phisicalPenet;
            $this->critChance = $critChance;
            $this->magicalDemage = $magicalDemage;
            $this->magicalPenet = $magicalPenet;
            $this->phisicalDef = $phisicalDef;
            $this->magicalDef = $magicalDef;
            $this->HP = $HP;
            $this->HPRegen = $HPRegen;
        }

    public function getInfoItems() {
        $a = new class{};
        foreach ($this as $key => $value) {
            $a->$key = $value;
        }
        return $a;
    }
}