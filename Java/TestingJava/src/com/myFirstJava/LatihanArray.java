package com.myFirstJava;

import java.util.Scanner;

public class LatihanArray {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int gajiPokok = 3000000;
        System.out.print("Berapa jumlah karyawan anda? : ");
        int jumlahKaryawan = input.nextInt();
        String[][] tabelKaryawan = new String[jumlahKaryawan][2];
        StringBuilder lebarTabel = new StringBuilder();

        for (int i = 0; i < tabelKaryawan.length; i++) {
            System.out.print("Silahkan masukan nama karyawan " + (i + 1) + ": ");
            String namaKaryawan = input.nextLine();
            System.out.print("Berapa lama beliau bekerja? : ");
            String totalPendapatan = Integer.toString(gajiPokok * input.nextInt());
            if (namaKaryawan.length() + totalPendapatan.length() > lebarTabel.length()) {
                for (int j = 0; i < (namaKaryawan.length() + totalPendapatan.length() + 7); j++) {
                    if (j == 0) {
                        lebarTabel = new StringBuilder();
                    } else {
                        lebarTabel.append("_");
                    }
                }
            }
            tabelKaryawan[i][0] = namaKaryawan;
            tabelKaryawan[i][1] = totalPendapatan;
        }

        System.out.println(lebarTabel);
        for (String[] strings : tabelKaryawan) {
            StringBuilder baris = new StringBuilder();
            for (String string : strings) {
                baris.append("| ").append(string).append(" ");
            }
            baris.append("|");
            System.out.println(baris);
        }
        System.out.println(lebarTabel);
    }
}
