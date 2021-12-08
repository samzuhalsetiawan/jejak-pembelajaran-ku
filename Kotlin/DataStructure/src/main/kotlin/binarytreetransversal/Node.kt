package binarytreetransversal

class Node(val name: String) {
    var leftNode: Node? = null
    var rightNode: Node? = null

    fun startPreorderTranversal() {
        print(this.name + " ")
        this.leftNode?.startPreorderTranversal()
        this.rightNode?.startPreorderTranversal()
    }

    fun startInorderTranversal() {
        this.leftNode?.startInorderTranversal()
        print(this.name + " ")
        this.rightNode?.startInorderTranversal()
    }

    fun startPostorderTranversal() {
        this.leftNode?.startPostorderTranversal()
        this.rightNode?.startPostorderTranversal()
        print(this.name + " ")
    }
}