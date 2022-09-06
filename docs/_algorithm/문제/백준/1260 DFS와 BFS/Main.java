import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	static int N; // 1~1000
	static int M; // 1~10000
	static int V;
	static boolean[][] ad = new boolean[1000][1000];
	
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		V = Integer.parseInt(st.nextToken())-1;
		for(int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			int n1 = Integer.parseInt(st.nextToken())-1;
			int n2 = Integer.parseInt(st.nextToken())-1;
			ad[n1][n2] = true;
			ad[n2][n1] = true;
		}
		br.close();
		
		boolean[] dfsVisit = new boolean[N];
		dfsVisit[V] = true;
		dfs(1, V, dfsVisit);
		System.out.println();
		bfs();
	}
	
	public static void dfs(int cnt, int idx, boolean[] visit) {
		if(cnt > N) return;
		
		System.out.print(idx+1 + " ");
		
		for(int i = 0; i < N; i++) {
			if(visit[i]) continue;
			if(ad[idx][i] == false) continue;
			
			visit[i] = true;
			dfs(cnt+1, i, visit);
		}
	}
	
	public static void bfs() {
		Queue<Integer> q = new LinkedList<Integer>();
		boolean[] visit = new boolean[N];
		
		q.add(V);
		visit[V] = true;
		
		while(!q.isEmpty()) {
			int cur = q.poll();
			System.out.print(cur+1 + " ");
			
			for(int i = 0; i < N; i++) {
				if(visit[i]) continue;
				
				if(ad[cur][i]) {
					q.add(i);
					visit[i] = true;	
				}
			}
		}
	}
}
