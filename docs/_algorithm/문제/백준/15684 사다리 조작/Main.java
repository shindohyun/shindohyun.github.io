import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static int N; // 2~10
	static int M; 
	static int H; // 1~30
	static int[][] map = new int[30][10];
	static int res = Integer.MAX_VALUE;
	
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		H = Integer.parseInt(st.nextToken());
		for(int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken())-1;
			int b = Integer.parseInt(st.nextToken())-1;
			map[a][b] = 1;
		}
		br.close();
		
		solution();
		System.out.println(res == Integer.MAX_VALUE ? -1 : res);
	}
	
	public static void solution() {
		for(int i = 0; i <= 3; i++) {
			if(dfs(i, 0, 0, 0)) {
				res = i;
				break;
			}
		}
	}
	
	public static boolean dfs(final int goal, int cnt, int sr, int sc) {
		if(cnt == goal) {
			if(play()) return true;
			return false;
		}
		
		for(int r = sr; r < H; r++) {
			for(int c = sc; c < N-1; c++) {
				if(map[r][c] == 1) continue;
				if((c+1 < N && map[r][c+1] == 1) || (c-1 >= 0 && map[r][c-1] == 1)) continue;
				
				map[r][c] = 1;
				if(dfs(goal, cnt+1, r, c)) return true;
				map[r][c] = 0;
			}
			sc = 0;
		}
		
		return false;
	}
	
	public static boolean play() {		
		//printMap();
		
		for(int c = 0; c < N-1; c++) {
			int cr = 0;
			int cc = c;
			int nr, nc;
			
			while(cr < H) {
				if(map[cr][cc] == 1) {
					nc = cc + 1;
				}
				else if(cc-1 >= 0 && map[cr][cc-1] == 1) {
					nc = cc - 1;
				}
				else{
					nc = cc;
				}
				
				
				nr = cr + 1;
								
				cr = nr;
				cc = nc;
			}
			
			if(c != cc) {
				return false;
			}
		}
		
		return true;
	}
	
	public static void printMap() {
		for(int r = 0; r < H; r++) {
			for(int c = 0; c < N; c++) {
				System.out.print(map[r][c] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
}