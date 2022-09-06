
public class main {

	public static void main(String[] args) {
		System.out.println("fibonacci Recursion result : " + fibonacciRecursion(7));
		System.out.println("fibonacci Dp result : " + fibonacciDp(7));
	}

	private static int fibonacciRecursion(int n) {
		if(n < 2) {
			return n;
		}
		else {
			return fibonacciRecursion(n-1) + fibonacciRecursion(n-2); 
		}
	}
	
	private static int fibonacciDp(int n) {
		int A[] = new int[1024]; //메모이제이션
		
		A[0] = 0;
		A[1] = 1;
		
		for(int i = 2; i <= n; i++) {
			A[i] = A[i-1] + A[i-2];
		}
		
		return A[n];
	}
}
