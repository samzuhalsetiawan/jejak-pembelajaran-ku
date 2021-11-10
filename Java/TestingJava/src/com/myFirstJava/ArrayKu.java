package com.myFirstJava;

import javax.swing.JOptionPane;

public class ArrayKu {
    public static void main(String[] args) {
        int data [][];

        int baris =Integer.valueOf(JOptionPane.showInputDialog("masukan baris"));
        int kolom =Integer.valueOf(JOptionPane.showInputDialog("masukan kolom"));

        data = new int[baris][kolom];
        data [0][0] =1;
        for (int i = 0; i < baris; i++) {
            for (int j = 0; j < kolom; j++) {
                data [i][j]=Integer.parseInt(JOptionPane.showInputDialog("masukan baris indek "+i+"masukan kolom ke "+j));
            }
        }
        for (int i = 0; i < baris; i++) {
            for (int j = 0; j < kolom; j++) {
                System.out.print(data [i][j]);
            }System.out.println("");
            System.out.println();
        }
    }
}
