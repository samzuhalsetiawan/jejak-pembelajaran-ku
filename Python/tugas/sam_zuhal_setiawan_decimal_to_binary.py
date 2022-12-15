# nama: Sam Zuhal Setiawan
# NIM: 2005176043
# Matkul/Prodi: Matematika Diskrit/Pendidikan Komputer

# Masukan nilai desimal yang ingin dikonversi disini
desimal=123

def DesimalKeBiner(desimal):
    if desimal >= 1:
        b = DesimalKeBiner(desimal // 2)
        return f"{b}{desimal % 2}"
    return f"{desimal % 2}"
    
print(f"Nilai Biner dari {desimal} adalah:")
biner = DesimalKeBiner(desimal)
print(biner[1:])