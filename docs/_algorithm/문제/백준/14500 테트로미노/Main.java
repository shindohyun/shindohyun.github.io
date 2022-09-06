import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	
	static int N; //4~500
	static int M; //4~500
	static int[][] map = new int[500][500];
	static boolean[][] visit = new boolean[500][500];
	static int[] dr = {1,-1,0,0};
	static int[] dc = {0,0,-1,1};
	static long max = Long.MIN_VALUE;
	static int[][] tec_r = {
			{0,1,0,1}, //ㅏ
			{0,1,0,1}, //ㅓ
			{0,0,1,-1}, //ㅜ
			{0,0,-1,1} //ㅗ
	};
	static int[][] tec_c = {
			{0,0,1,-1}, //ㅏ
			{0,0,-1,1}, //ㅓ
			{0,1,0,1}, //ㅜ
			{0,1,0,1} //ㅗ
	};	
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		for(int i=0;i<N;i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0;j<M;j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		br.close();
		
		solution();
		System.out.println(max);
	}
	
	private static void solution() {
		for(int i=0;i<N;i++) {
			for(int j=0;j<M;j++) {
				visit[i][j] = true;
				dfs(i,j,1,map[i][j]);
				visit[i][j] = false;
				
				int nr, nc;
				long sum;
				//ㅜ 검사
				for(int d=0;d<4;d++) {
					sum = 0;
					nr=i;
					nc=j;
					boolean find = false;
					for(int t =0;t<4;t++) {
						nr = nr+tec_r[d][t];
						nc = nc+tec_c[d][t];
						
						if(!(nr>=0&&nr<N&&nc>=0&&nc<M)) break;
						
						if(t==3) find = true;
						sum+=map[nr][nc];
					}
					
					if(find && sum > max) {
						max = sum;
					}
				}
			}
		}
	}
	
	private static void dfs(int r, int c, int depth, long sum) {
		if(depth == 4) {
			if(sum > max) max = sum;
			return;
		}
				
		int nr, nc;
		for(int d=0;d<4;d++) {
			nr = r + dr[d];
			nc = c + dc[d];
			
			if(!(nr>=0&&nr<N&&nc>=0&&nc<M) || visit[nr][nc]) continue;
			
			visit[nr][nc] = true;
			dfs(nr,nc,depth+1,sum+map[nr][nc]);
			visit[nr][nc] = false;
		}
	}
}