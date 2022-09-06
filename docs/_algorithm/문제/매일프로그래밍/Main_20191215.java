import java.util.Scanner;

public class Main_20191215 {
	static int arr[];
	static int K;
	static boolean res = false;
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int arrSize = sc.nextInt();
		arr = new int[arrSize];
		for(int i = 0; i < arrSize; i++) {
			arr[i] = sc.nextInt();
		}
		K = sc.nextInt();
		sc.close();
		
		solution();
		System.out.println(res);
	}
	
	public static void solution() {
		rec(0, 0, 0);
	}
	
	public static void rec(int start, int sum, int cnt) {
		if(sum == K && cnt == 3) {
			res = true;
			return;
		}
		
		if(start >= arr.length || cnt > 3) {
			return;
		}
		
		for(int i = start; i < arr.length; i++) {
			rec(i + 1, sum + arr[i], cnt + 1);
			rec(i + 1, sum, cnt);
		}
	}
}
