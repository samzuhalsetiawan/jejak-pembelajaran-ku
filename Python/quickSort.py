def quickSortString(myList):
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
        sectorA = quickSortString(sectorA)

    if len(sectorB) > 1:
        sectorB = quickSortString(sectorB)

    while ganda != 0:
        sectorA.append(partition)
        ganda -= 1

    return sectorA + sectorB


def quickSortNumber(myList):
    low = 0
    high = len(myList) - 1
    midle = (high - low) // 2
    partition = myList[midle]
    sectorA = []
    sectorB = []
    ganda = 0

    for item in myList:
        if item < partition:
            sectorA.append(item)
        elif item > partition:
            sectorB.append(item)
        else:
            ganda += 1

    if len(sectorA) > 1:
        sectorA = quickSortNumber(sectorA)

    if len(sectorB) > 1:
        sectorB = quickSortNumber(sectorB)

    while ganda != 0:
        sectorA.append(partition)
        ganda -= 1

    return sectorA + sectorB
