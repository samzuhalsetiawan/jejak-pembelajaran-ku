import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;

/**
 * Solution
 */
public class Solution {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        // mengambil 3 input dari user
        String[] userInputs = new String[3];
        for (int i = 0; i < 3; i++) {
            String inputUser = reader.readLine();
            userInputs[i] = inputUser;
        }

        // memisahkan antara sisi dan identitas
        String[] identitas = new String[3];
        Integer[] sides = new Integer[3];
        for (int i = 0; i < userInputs.length; i++) {
            String userInput = userInputs[i];
            String identity = userInput.substring(0, userInput.length() - 2);
            int side = Integer.parseInt(userInput.substring(userInput.length() - 2));
            identitas[i] = identity;
            sides[i] = side;
        }
        Arrays.sort(sides, Collections.reverseOrder());
        
        // menentukan jenis segitiga
        int a = sides[0];
        int b = sides[1];
        int c = sides[2];
        if(a >= b + c) {
            System.out.println("Bukan Segitiga");
        } else if(Math.pow(a, 2) == Math.pow(b, 2) + Math.pow(c, 2)) {
            System.out.println("Segitiga Siku - Siku");
        } else if(Math.pow(a, 2) > Math.pow(b, 2) + Math.pow(c, 2)) {
            System.out.println("Segitiga Tumpul");
        } else if(Math.pow(a, 2) < Math.pow(b, 2) + Math.pow(c, 2)) {
            System.out.println("Segitiga Lancip");
        }
        if (a == b && b == c) {
            System.out.println("Segitiga Sama Sisi");
        } else if (a == b || a == c || b == c) {
            System.out.println("Segitiga Sama Kaki");
        }

        // menampilkan identitas segitiga
        System.out.println(String.join("", identitas));

        // menampilkan keliling dan luas segitiga
        int keliling = a + b + c;
        float luas = b * c / 2;
        System.out.println("Keliling: " + keliling);
        System.out.println("Luas: " + luas);
    }
}