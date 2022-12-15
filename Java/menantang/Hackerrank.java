import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Hackerrank {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        String inputUser = reader.readLine();
        int inputAngka = Integer.parseInt(inputUser);
        if(inputAngka == 196) {
            System.out.println("Bilangan dilarang!");
            System.exit(0);
        } else {
            cariBilanganPalindrom(inputUser);    
        }
    }

    public static void cariBilanganPalindrom(String inputUser) {
        String stringKebalik = "";
        char tempChar;

        for (int i = 0; i < inputUser.length(); i++) {
            tempChar = inputUser.charAt(i);
            stringKebalik = tempChar + stringKebalik;
        }

        int angka1 = Integer.parseInt(inputUser);
        int angka2 = Integer.parseInt(stringKebalik);
        int hasil = angka1 + angka2;

        if(angka1 == angka2){
            System.out.println(inputUser + " merupakan bilangan palindrome");
        } else {
            System.out.println(inputUser + " + " + stringKebalik + " = " + hasil);
            cariBilanganPalindrom(Integer.toString(hasil));
        }
    }
}
