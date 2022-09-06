import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	
	static int N; // 1~1000
	static int M;
	static boolean[][] ad = new boolean[1000][1000];
	static boolean[] visit = new boolean[1000];
	static int res = 0;
	
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		for(int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			int u = Integer.parseInt(st.nextToken())-1;
			int v = Integer.parseInt(st.nextToken())-1;
			ad[u][v] = true;
			ad[v][u] = true;
		}
		br.close();

		solution();
		System.out.println(res);
	}
	
	public static void solution() {
		for(int i = 0; i < N; i++) {
			if(visit[i]) continue;
			res++;
			dfs(i);
		}
	}
	
	public static void dfs(int idx) {
		for(int i = 0; i < N; i++) {
			if(visit[i]) continue;
			
			if(ad[idx][i]) {
				visit[i] = true;
				dfs(i);
			}
		}
	}
}
