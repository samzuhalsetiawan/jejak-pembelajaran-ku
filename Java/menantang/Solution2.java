import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Solution2 {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        String[] input1 = reader.readLine().split(" ");
        String[] input2 = reader.readLine().split(" ");

        int banyakAntrian = Integer.parseInt(input1[0]);
        int banyakBus = Integer.parseInt(input1[1]);
        int[] banyakKursiKosong = new int[input2.length];
        for (int i = 0; i < input2.length; i++) {
            banyakKursiKosong[i] = Integer.parseInt(input2[i]);
        }

        int pendapatan = 0;
        int[] kursiKosongCopy = banyakKursiKosong.clone();
        for (int i = 0; i < banyakAntrian; i++) {
            for (int j = 0; j < banyakBus; j++) {
                pendapatan += kursiKosongCopy[j];
            }
        }
    }
}
