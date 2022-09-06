import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static int R, C; //6~50
	static int T; //1~1000
	static int[][] map = new int[50][50];
	//상하좌우
	static int[] dr = {-1, 1, 0, 0};
	static int[] dc = {0, 0, -1, 1};
	static int[] cw = {3, 1, 2, 0}; //시계방향(우-하-좌-상)
	static int[] ccw = {3, 0, 2, 1}; //반시계방향(우-상-좌-하	)
	static int top, bot;
	static int total = 0;
	
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		R = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());
		T = Integer.parseInt(st.nextToken());
		for(int i = 0; i < R; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j < C; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
				if(map[i][j] == -1) {
					if(top == 0) top = i;
					else bot = i;
				}
			}
		}
		br.close();
		
		solution();
		System.out.println(total);
	}
	
	public static void solution() {
		while(T-- > 0) {
			//미세먼지 확산	
			diffuse();

			//공기 순환
			rotate();
		}
		
		for(int i = 0; i < R; i++) {
			for(int j = 0; j < C; j++) {
				if(map[i][j] == -1) continue;
				total += map[i][j];
			}
		}
	}
	
	public static void rotate() {
		int[][] newMap = map.clone();
		for(int i = 0; i < map[0].length; i++) {
			newMap[i] = map[i].clone();
		}
		
		int nr, nc;
		int cr, cc;
		
		//시계반대방향 순환
		cr = top;
		cc = 1;
		for(int d=0; d<4; d++) {
			while(true) {
				nr = cr + dr[ccw[d]];
				nc = cc + dc[ccw[d]];
				
				//벽에 부딪히거나 공기청정기를 만나면 종료한다.
				if(!(nr >= 0 && nr <= top && nc >= 0 && nc < C) || (nr == top && nc == 0)) break;
				
				newMap[nr][nc] = map[cr][cc];
				
				cr = nr;
				cc = nc;
			}
		}
		
		//시계방향 순환
		cr = bot;
		cc = 1;
		for(int d=0; d<4; d++) {
			while(true) {
				nr = cr + dr[cw[d]];
				nc = cc + dc[cw[d]];
				
				//벽에 부딪히거나 공기청정기를 만나면 종료한다.
				if(!(nr >= bot && nr < R && nc >= 0 && nc < C) || (nr == bot && nc == 0)) break;
				
				newMap[nr][nc] = map[cr][cc];
				
				cr = nr;
				cc = nc;
			}
		}
		
		newMap[top][1] = 0;
		newMap[bot][1] = 0;
		map = newMap;
	}
	
	public static void diffuse() {
		int[][] addMap = new int[R][C];
		
		for(int r = 0; r < R; r++) {
			for(int c = 0; c < C; c++) {
				if(map[r][c] <= 0) continue;
				
				int v = map[r][c];
				int addCnt = 0;
				int nr, nc;
				for(int d = 0; d < 4; d++) {
					nr = r + dr[d];
					nc = c + dc[d];
					if(!(nr >= 0 && nr < R && nc >= 0 && nc < C) || map[nr][nc] == -1) continue;
					
					addMap[nr][nc] += v/5;
					addCnt++;
				}
				
				map[r][c] -= ((map[r][c]/5)*addCnt);
			}
		}
		
		//합산하기
		for(int r = 0; r < R; r++) {
			for(int c = 0; c < C; c++) {
				map[r][c] += addMap[r][c];
			}
		}
	}
	
	public static void printMap() {
		for(int i = 0; i < R; i++) {
			for(int j = 0; j < C; j++) {
				System.out.print(map[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
}
