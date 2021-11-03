'''
Nama: Sam Zuhal Setiawan
NIM: 2005176043
Prodi: Pendidikan Komputer 2020
'''


def linierSearch(list, item):
    for i in range(len(list)):
        if (list[i] == item and type(list[i]) == type(item)):
            return f"ditemukan ({item}) pada indeks ke ({i})"
    return "data tidak ditemukan"


print(linierSearch([1, 2, 3, 4, 5, 6], 1))  # ditemukan (1) pada indeks ke (0)
print(linierSearch([1, 2, 3, 4, 5, 6], 7))  # data tidak ditemukan
