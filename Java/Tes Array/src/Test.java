import java.util.Scanner;

public class Test {

    public static int findDuplicate(int[] a, int n) {
        for (int i = 0; i <= n; i++) {
            for (int j = i+1; j <= n; j++) {
                if (a[i] == a[j]) {
                    return  a[i];
                }
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] a = new int[n+1];
        for (int i = 0; i < n+1; i++) {
            a[i] = sc.nextInt();
        }
        int ans = findDuplicate(a, n);
        System.out.println("Duplicate element is: " +ans);
    }
}
