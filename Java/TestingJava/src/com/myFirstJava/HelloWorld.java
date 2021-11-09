package com.myFirstJava;

import java.util.Arrays;

public class HelloWorld {
    public static void main(String[] args) {
        int[] myArray = new int[10];
        for (int i = 0; i < myArray.length; i++) {
            myArray[i] = i;
        }
//        String[] myArray = new String[10];
//        myArray[3] = "Sam";
        System.out.println(Arrays.toString(myArray));
    }
}
