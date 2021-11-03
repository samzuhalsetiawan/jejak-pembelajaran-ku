class Node:
    def __init__(self, nama) -> None:
        self.nodeName = nama
        self.neighbors = []
        self.visited = False

    def createEdge(self, v, undirected=True):
        self.neighbors.append(v)
        if (undirected):
            v.neighbors.append(self)


node1 = Node("satu")
node2 = Node("dua")
node3 = Node("tiga")
node4 = Node("empat")
node5 = Node("lima")
node6 = Node("enam")
node7 = Node("tujuh")

node1.createEdge(node2)
node1.createEdge(node3)
node2.createEdge(node4)
node2.createEdge(node5)
node3.createEdge(node6)
node3.createEdge(node7)


def postOrder(node):
    stack = []
    while True:
        if (not node.visited):
            stack.append(node)
            node.visited = True

        if (len(stack[-1].neighbors)):
            for neighbors in stack[-1].neighbors:
                if (not neighbors.visited):
                    node = neighbors
                    break
            else:
                print(node.nodeName, end=" ")
                stack.pop()
                if (len(stack)):
                    node = stack[-1]
        else:
            print(node.nodeName, end=" ")
            stack.pop()
            node = stack[-1]

        if (not len(stack)):
            break


def postOrderRecursive(node):
    node.visited = True

    for neighbors in node.neighbors:
        if (not neighbors.visited):
            postOrderRecursive(neighbors)

    print(node.nodeName, end=" ")


postOrder(node1)
postOrderRecursive(node1)
