a = [5, 5, 5, 2, 4, 3, 1, 3, 10, 9, 6, 7]


def quickSort(myList):
    low = 0
    high = len(myList) - 1
    midle = (high - low) // 2
    partition = str(myList[midle])
    sectorA = []
    sectorB = []
    ganda = 0

    for item in myList:
        item = str(item)
        if item < partition:
            sectorA.append(item)
        elif item > partition:
            sectorB.append(item)
        else:
            ganda += 1

    if len(sectorA) > 1:
        sectorA = quickSort(sectorA)

    if len(sectorB) > 1:
        sectorB = quickSort(sectorB)

    while ganda != 0:
        sectorA.append(partition)
        ganda -= 1

    return sectorA + sectorB


a = quickSort(a)
print(a)


def binarySearch(list, item):
    low = 0
    high = len(list) - 1

    while low <= high:
        midle = low + (high - low) // 2
        if type(list[midle]) != type(item):
            item = str(item)
            list[midle] = str(list[midle])
        if list[midle] == item:
            indeks = [midle]
            loop = True
            loopIndx = 1
            while loop:
                if midle - loopIndx >= 0:
                    if list[midle - loopIndx] == list[midle]:
                        loop = True
                        indeks.append(midle - loopIndx)
                    else:
                        loop = False
                if midle + loopIndx < len(list):
                    if list[midle + loopIndx] == list[midle]:
                        loop = True
                        indeks.append(midle + loopIndx)
                    else:
                        loop = False
                if midle - loopIndx >= 0 or midle + loopIndx < len(list):
                    loopIndx += 1
                else:
                    loop = False
            indeks.sort()
            if len(indeks) == 1:
                return indeks[0]
            return indeks
        elif list[midle] < item:
            low = midle + 1
        else:
            high = midle - 1

    return -1


print(binarySearch(a, 5))
