from collections import defaultdict

g = {1: [2, 3], 2: [4, 5], 4: [], 5: [], 3: [6, 7], 6: [], 7: []}

visited = {}

for node in g:
    visited[node] = []
    for child in g[node]:
        visited[node].append(False)

stack = []

stack.append(1)

count = 0
for node in g[stack[-1]]:
    if (count == 0):
        stack.append(g[stack[-1]][count])
        print(node, end=' ')
