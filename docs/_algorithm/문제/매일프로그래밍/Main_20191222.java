import java.util.Scanner;

public class Main_20191222{
	static int N;
	static int[] binaryArr;
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt();
		binaryArr = new int[N];
		sc.close();
		
		solution();
	}
	
	private static void solution() {
		rec(N, "", 0);
	}
	
	private static void rec(int n, String str, int last) {
		if(n == 0) {
			System.out.println(str);
			return;
		}
		
		rec(n - 1, str + "0", 0);
		
		if(last == 0) {
			rec(n - 1, str + "1", 1);
		}
	}
	
	private static void solution2() {
		print();
		for(int i = N - 1; i >= 0; i--) {
			binaryArr[i] = 1;
			print();
			rec2(i, N-1);
			binaryArr[i] = 0;
		}
	}
	
	private static void rec2(int sp, int mp) {
		for(int i = mp; i > sp; i--) {
			binaryArr[i] = 1;
			print();
			rec2(i, mp);
			binaryArr[i] = 0;
		}
	}
	
	private static void print() {
		String printString = "";
		boolean doPrint = true;
		for(int j = 0; j < N; j++) {
			int left = j - 1;
			int right = j + 1;
			
			if(binaryArr[j] == 1) {
				if(left >= 0 && binaryArr[left] == 1) {
					doPrint = false;
				}
				if(right < N && binaryArr[right] == 1) {
					doPrint = false;
				}	
			}
			
			if(!doPrint)
				break;
			
			printString += binaryArr[j];
		}
		if(doPrint)
			System.out.println(printString);
	}
}
