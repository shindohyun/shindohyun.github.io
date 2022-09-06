import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static int T, N;
	static int[] arr = new int[100];
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		T = Integer.parseInt(br.readLine());
		StringTokenizer st = null;
		for(int t = 0; t < T; t++) {
			st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			for(int i = 0; i < N; i++) {
				arr[i] = Integer.parseInt(st.nextToken());
			}
			
			solution();
		}
		br.close();
	}
	
	private static void solution() {
		long sum = 0;
		
		//모든 쌍의 조합 구하기
		for(int i = 0; i < N-1; i++) {
			for(int j = i+1; j < N; j++) {
				int num1 = arr[i];
				int num2 = arr[j];
				sum += GCD(num1, num2);
			}
		}
		
		System.out.println(sum);
	}
	
	private static int GCD(int num1, int num2) {
		while(num2 != 0) {
			int temp = num1%num2;
			num1 = num2;
			num2 = temp;
		}
		
		return num1;
	}
}
