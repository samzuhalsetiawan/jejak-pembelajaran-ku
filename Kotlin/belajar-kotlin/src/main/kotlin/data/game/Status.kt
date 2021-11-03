package data.game

interface Status {
    var hp: Int
    var phisicalDemage: Int
    var magicalDemage: Int
    var phisicalPenetration: Int
    var magicalPenetration: Int
    var phisicalDefence: Int
    var magicalDefance: Int
    var critChance: Int
}