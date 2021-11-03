from collections import defaultdict


class Graph:
    def __init__(self):
        self.graph = defaultdict(list)
        self.visited = set()

    def addEdge(self, u, v):
        self.graph[u].append(v)

    def dfs(self, root):
        self.visited.add(root)
        print(root, end=' ')

        for node in self.graph[root]:
            if (node not in self.visited):
                self.dfs(node)


g = Graph()
g.addEdge(0, 1)
g.addEdge(0, 2)
g.addEdge(1, 2)
g.addEdge(2, 0)
g.addEdge(2, 3)
g.addEdge(3, 3)

g.dfs(2)

print(g.graph)
