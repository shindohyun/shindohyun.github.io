import java.util.Scanner;

/* TEST DATA
5
3 2 -2 5 -3

6
1 1 2 -1 2 -1

4
1 2 3 -4
 */
public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		int A[] = new int[N];
		for(int i = 0; i < N; i++) {
			A[i] = sc.nextInt();
		}
		sc.close();
		
		System.out.println(solution(A));
	}
	
	private static int solution(int A[]) {
		int result = 0;
		
		boolean find = false;
		for(int i = 0; i < A.length; i++) {
			for(int j = i; j < A.length; j++) {
				if(A[i] == A[j] * -1) {
					find = true;
				}
			}
			
			if(find) {
				result = A[i];
				break;
			}
		}
		return result;
	}

}
