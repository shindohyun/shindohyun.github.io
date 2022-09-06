import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static int N, M; // 1~8
	static int[][] map = new int[8][8];
	static Cctv[] cctvs = new Cctv[8];
	static int cctvCnt = 0;
	static int res = Integer.MAX_VALUE;
	static final int[] dr = {-1,1,0,0};
	static final int[] dc = {0,0,-1,1};
	static final int[][][] dirInfo = {
			{
				{0},{1},{2},{3}
			},
			{
				{0,1},{2,3}
			},
			{
				{0,3},{3,1},{1,2},{2,0}
			},
			{
				{2,0,3},{0,3,1},{3,1,2},{1,2,0}
			},
			{
				{0,1,2,3}
			}
	};
	
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		for(int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j < M; j++) {
				int input = Integer.parseInt(st.nextToken());
				if(input >= 1 && input <= 5) {
					cctvs[cctvCnt++] = new Cctv(new Point(i, j), input);
				}
				else {
					map[i][j] = input;
				}
			}
		}
		br.close();
		solution();
		System.out.println(res);
	}
	
	public static void solution() {
		recursive(0, new int[cctvCnt]);
	}
	
	public static void recursive(int idx, int[] dirSet) {
		if(idx >= cctvCnt) {
			int[][] copy = map.clone();
			for(int i = 0; i < N; i++) {
				copy[i] = map[i].clone();
			}
			
			for(int i = 0; i < cctvCnt; i++) {
				Cctv cctv = cctvs[i];
				int[] dirs = dirInfo[cctv.type-1][dirSet[i]];
				for(int d : dirs) {
					int r = cctv.pt.r;
					int c = cctv.pt.c;
					copy[r][c] = 7;
					
					int nr, nc;
					while(true) {
						nr = r + dr[d];
						nc = c + dc[d];
						
						if(!(nr >= 0 && nr < N && nc >= 0 && nc < M) || copy[nr][nc] == 6) break;
						
						copy[nr][nc] = 7;
						r = nr;
						c = nc;
					}
				}
			}
			
			int emptyCnt = 0;
			for(int r = 0; r < N; r++) {
				for(int c = 0; c < M; c++) {
					if(copy[r][c] == 0) emptyCnt++;
				}
			}
			
			if(res > emptyCnt) res = emptyCnt;
			
			return;
		}
		
		Cctv cctv = cctvs[idx];
		int type = cctv.type-1;
		int dirSetCnt = dirInfo[type].length;
		
		for(int i = 0; i < dirSetCnt; i++) {
			dirSet[idx] = i;
			recursive(idx+1, dirSet);
		}
	}
	
	static class Cctv{
		public Point pt;
		public int type;
		public Cctv(Point pt, int type){
			this.pt = pt;
			this.type = type;
		}
	}
	static class Point {
		public int r, c;
		public Point(int r, int c) {
			this.r = r;
			this.c = c;
		}
	}
}
