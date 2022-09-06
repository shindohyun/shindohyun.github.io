import java.util.Scanner;

public class Main {
	static int N; //1~1,000,000
	static int min = Integer.MAX_VALUE;
	static int[] memo = new int[1000001];
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt();
		
		solution();
		System.out.println(min);
	}
	
	private static void solution() {
		min = dp(1)-1;
	}
	
	private static int dp(int n) {	
		if(n==N) {
			return 1;
		}
		if(n>N) {
			return -1;
		}
		
		if(memo[n] != 0) return memo[n]+1;
		
		int minRes = Integer.MAX_VALUE;

		int res = dp(n*3);
		if(res!=-1) minRes = res;
				
		res = dp(n*2);
		if(res!=-1) {
			if(minRes > res)
				minRes= res;
		}
		
		res = dp(n+1);
		if(res!=-1) {
			if(minRes > res)
				minRes= res;
		}
		
		if(minRes != Integer.MAX_VALUE) {
			memo[n]=minRes;
			return minRes+1;
		}
		else
			return -1;
	}
}
