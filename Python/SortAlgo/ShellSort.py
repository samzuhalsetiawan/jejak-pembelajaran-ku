'''
nama: Sam Zuhal Setiawan
NIM: 2005176043
metode pengurutan: Shell Sort Algorithm
'''

import numpy as np

# saya menggunakan numpy untuk list
# generate list dengan 200 angka random
angkaRandoms = np.random.randint(200, size=200)


def shellSort(array, n):  # Inisialisasi Fungsi shellSort Algorithm
    # inisialisasi nilai intervalnya, disini menggunakan panjang list (n) // 2
    interval = n // 2

    # program akan terus dijalankan sampai benar-benar terurut
    while interval > 0:
        for i in range(interval, n):
            # simpan bilangan yg sebelah kanan ke dalam variabel temporari
            # gunanya untuk ditukar jika jika angka yg di bandingkan tidak berurut
            temp = array[i]
            j = i
            while j >= interval and array[j - interval] > temp:
                # logic untuk menukar (swap) angka
                array[j] = array[j - interval]
                j -= interval
            # logic untuk menukar (swap) angka
            array[j] = temp
        # setelah satu periode pengurutan, bagi 2 lagi intervalnya
        # lakukan terus sampai interval bernilai 0, dan data telah terurut
        interval //= 2


# cari panjang dari list bilangan random
panjangData = len(angkaRandoms)
# tampilkan data yg belum terurut
print('List Bilangan yang belum terurut: ')
print(angkaRandoms)
# panggil Fungsi shellSort
shellSort(angkaRandoms, panjangData)
# tampilkan data yg telah terurut
print('Sorted Array in Ascending Order:')
print(angkaRandoms)
