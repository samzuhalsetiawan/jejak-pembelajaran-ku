// const graph = {
//     0: [1, 2],
//     1: [2],
//     2: [0, 3],
//     3: [3]
// }

// const visited = [];

// function DFS(root, graph) {
//     // console.log({ "root": root, "child": graph[root], "visit": visited });
//     visited.push(root);
//     console.log(root);

//     graph[root].forEach(node => {
//         if (visited.find(v => v == node) == undefined) {
//             DFS(node, graph);
//         }
//     });

// }

// DFS(2, graph);

class Graph {
    constructor() {
        this.graf = {}
    }
    addEdge(u, v) {
        if (Array.isArray(this.graf[u])) {
            this.graf[u].push(v);
        } else {
            this.graf[u] = [v];
        }
    }
    #visited = [];
    DFS(root) {
        this.#visited.push(root);
        console.log(root);
        this.graf[root].forEach(node => {
            if (this.#visited.find(v => v == node) == undefined) {
                this.DFS(node);
            }
        });
    }
}

const myGraph = new Graph();
myGraph.addEdge(0, 1);
myGraph.addEdge(0, 2);
myGraph.addEdge(1, 2);
myGraph.addEdge(2, 0);
myGraph.addEdge(2, 3);
myGraph.addEdge(3, 3);

myGraph.DFS(2);

console.log(myGraph.graf)