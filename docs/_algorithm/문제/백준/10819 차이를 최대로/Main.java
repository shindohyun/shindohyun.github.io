import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

	static int N;
	static int[] p = new int[8];
	static int[] newP = new int[8];
	static boolean[] visit = new boolean[8];
	static int max = 0;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		StringTokenizer st = new StringTokenizer(br.readLine());
		for(int i = 0; i < N; i++) {
			p[i] = Integer.parseInt(st.nextToken());
		}
		br.close();
		
		solution();
		System.out.println(max);
	}

	private static void solution() {
		permutation(0);
	}
	
	private static void permutation(int cnt) {
		if(cnt == N) {
//			for(int i = 0; i < N; i++) {
//				System.out.print(newP[i] + " ");
//			}
//			System.out.println();
			calculate();
			return;
		}
		
		if(cnt > N) return;
		
		for(int i = 0; i < N; i++) {
			if(visit[i]) continue;
			
			visit[i] = true;
			newP[cnt] = p[i];
			permutation(cnt+1);
			newP[cnt] = 0;
			visit[i] = false;
		}
	}
	
	private static void calculate() {
		int sum = 0;
		for(int i = 0; i < N-1; i++) {
			sum += Math.abs(newP[i] - newP[i+1]);
		}
		
		if(max < sum) max=sum;
	}
}
