package binarytreetransversal

fun main() {
    val nodeA = Node("A")
    val nodeB = Node("B")
    val nodeC = Node("C")
    val nodeD = Node("D")
    val nodeE = Node("E")
    val nodeF = Node("F")
    val nodeG = Node("G")
    val nodeH = Node("H")
    val nodeI = Node("I")
    val nodeJ = Node("J")
    val nodeK = Node("K")
    val nodeL = Node("L")
    val nodeM = Node("M")
    val nodeN = Node("N")
    val nodeO = Node("O")

    nodeA.leftNode = nodeB
    nodeA.rightNode = nodeC
    nodeB.leftNode = nodeD
    nodeB.rightNode = nodeE
    nodeC.leftNode = nodeF
    nodeC.rightNode = nodeG
    nodeD.leftNode = nodeH
    nodeD.rightNode = nodeI
    nodeE.leftNode = nodeJ
    nodeE.rightNode = nodeK
    nodeF.leftNode = nodeL
    nodeF.rightNode = nodeM
    nodeG.leftNode = nodeN
    nodeG.rightNode = nodeO

    nodeA.startPreorderTranversal()
    println()
    nodeA.startInorderTranversal()
    println()
    nodeA.startPostorderTranversal()

}