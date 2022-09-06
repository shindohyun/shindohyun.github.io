import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static int N, M; // 1~100
	static int[] baskets = new int[100];
	
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		for(int n = 0; n < N; n++) {
			baskets[n] = n+1;
		}
		for(int m = 0; m < M; m++) {
			st = new StringTokenizer(br.readLine());
			int i = Integer.parseInt(st.nextToken())-1;
			int j = Integer.parseInt(st.nextToken())-1;
			swap(i, j);
		}
		br.close();
		for(int i = 0; i < N; i++) {
			System.out.print(baskets[i] + " ");
		}
	}
	
	public static void swap(int i, int j) {
		int temp = baskets[i];
		baskets[i] = baskets[j];
		baskets[j] = temp;
	}

}
