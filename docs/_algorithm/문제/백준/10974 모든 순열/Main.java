import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
	
	static int N;
	static int[] p = new int[8];
	static boolean[] visit = new boolean[8];
	
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		br.close();
		
		solution();
	}
	
	private static void solution() {
		permutation(0);
	}
	
	private static void permutation(int cnt) {
		if(cnt == N) {
			for(int i = 0; i < cnt; i++) {
				System.out.print(p[i] + " ");
			}
			System.out.println();
			return;
		}
		
		for(int i = 0; i < N; i++) {
			if(visit[i]) continue;
			
			visit[i] = true;
			p[cnt] = i+1;
			permutation(cnt+1);
			p[cnt] = 0;
			visit[i] = false;
		}
	}
}
