import qrtools

qr = qrtools.QR()

qr.decode("dicoding1.png")

print(qr.data)
