import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	static int N; // 1~100
	static int E;
	static boolean[][] ad = new boolean[100][100];
	static boolean[] visit = new boolean[100];
	static int res = 0;
	
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		N = Integer.parseInt(br.readLine());
		E = Integer.parseInt(br.readLine());
		for(int i = 0; i < E; i++) {
			st = new StringTokenizer(br.readLine());
			int n1 = Integer.parseInt(st.nextToken())-1;
			int n2 = Integer.parseInt(st.nextToken())-1;
			ad[n1][n2] = true;
			ad[n2][n1] = true;
		}
		br.close();
		
		solution();
		System.out.println(res);
	}
	
	public static void solution() {
		visit[0] = true;
		dfs(0,0);
	}
	
	public static void dfs(int cnt, int idx) {
		if(cnt >= N) return;
				
		for(int i = 0; i < N; i++) {
			if(!ad[idx][i] || visit[i]) continue;
			res++;
			visit[i] = true;
			dfs(cnt+1, i);
		}
	}
}
