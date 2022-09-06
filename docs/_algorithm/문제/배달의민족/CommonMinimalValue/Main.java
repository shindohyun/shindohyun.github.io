import java.util.Arrays;
import java.util.Scanner;

public class Main {
	
	public static void main(String[] args) {
		int N, M;
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt();
		M = sc.nextInt();
		int A[] = new int[N];
		int B[] = new int[M];
		for(int i = 0; i < N; i++) {
			A[i] = sc.nextInt();
		}
		
		for(int i = 0; i < M; i++) {
			B[i] = sc.nextInt();
		}
		sc.close();
		
		System.out.println(solution(A, B));
	}
	
	private static int solution(int[] A, int[] B) {
		int n = A.length;
        int m = B.length;;
        
        Arrays.sort(A);
        Arrays.sort(B);
        
        int i = 0;
        
        for (int k = 0; k < n; k++) {
            //if (i < m - 1 && B[i] < A[k])
        	while(i < m - 1 && B[i] < A[k]) //수정 부분
                i += 1;
        	
            if (A[k] == B[i])
                return A[k];
        }
        
        return -1;
    }
}
