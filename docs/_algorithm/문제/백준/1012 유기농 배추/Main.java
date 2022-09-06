import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static int T;
	static int M; //1~50 가로 c
	static int N; //1~50 세로 r
	static int K; //1~2500
	static int[][] map = new int[50][50];
	static int cnt = 0;
	static int[] dr= {-1,1,0,0};
	static int[] dc= {0,0,-1,1};
	
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		T = Integer.parseInt(br.readLine());
		StringTokenizer st = null;
		for(int t=0; t<T; t++) {
			clearMap();
			cnt = 0;
			
			st = new StringTokenizer(br.readLine());
			M = Integer.parseInt(st.nextToken());
			N = Integer.parseInt(st.nextToken());
			K = Integer.parseInt(st.nextToken());
			for(int k=0; k<K; k++) {
				st = new StringTokenizer(br.readLine());
				int x = Integer.parseInt(st.nextToken());
				int y = Integer.parseInt(st.nextToken());
				map[y][x] = 1;
			}
			
			solution();
			System.out.println(cnt);
		}
		br.close();
	}
	
	private static void solution() {
		for(int r=0; r<N; r++) {
			for(int c=0; c<M; c++) {
				if(map[r][c] == 1) {
					dfs(r, c);
					cnt++;
				}
			}
		}
	}
	
	private static void dfs(int cr, int cc) {
		map[cr][cc] = 2; 
		
		int nr,nc;
		for(int d=0; d<4; d++) {
			nr = cr+dr[d];
			nc = cc+dc[d];
			
			if(!(nr>=0&&nr<N&&nc>=0&&nc<M) || map[nr][nc] != 1) continue;
			
			dfs(nr,nc);
		}
	}
	
	private static void clearMap() {
		for(int i=0; i<50; i++) {
			for(int j=0; j<50; j++) {
				map[i][j] = 0;
			}
		}
	}
}
