import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	static int N, M; // 2~100
	static int[][] map = new int[100][100];
	static int[] dr = {-1,1,0,0};
	static int[] dc = {0,0,-1,1};
	static int cnt = 0;
	static int time = 0;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		for(int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j < M; j++) {
				int input = Integer.parseInt(st.nextToken());
				map[i][j] = input;
				if(input==1) cnt++;
			}
		}
		br.close();
		
		solution();
		System.out.println(time);
		System.out.println(cnt);
	}
	
	public static void solution() {		
		while(true) {
			time++;
			
			border();
			int removeCnt = remove();
			
//			printMap();
			
			if(cnt - removeCnt == 0) {
				break;
			}
			
			cnt -= removeCnt;
		}
	}
	
	public static void border() {
		Queue<Point> q = new LinkedList<Point>();
		boolean[][] visit = new boolean[N][M];
		q.add(new Point(0,0));
		
		while(!q.isEmpty()) {
			Point cur = q.poll();
			int nr, nc;
			for(int d = 0; d < 4; d++) {
				nr = cur.r + dr[d];
				nc = cur.c + dc[d];
				
				if(!(nr >= 0 && nr < N && nc >= 0 && nc < M) || visit[nr][nc] || map[nr][nc] == 2) continue;
				if(map[nr][nc] == 1) {
					map[nr][nc] = 2;
					continue;
				}
				
				q.add(new Point(nr,nc));
				visit[nr][nc] = true;
			}
		}
	}
	
	public static int remove() {
		int removeCnt = 0;
		for(int r = 0; r < N; r++) {
			for(int c = 0; c < M; c++) {
				if(map[r][c] == 2) {
					removeCnt++;
					map[r][c] = 0;
				}
			}
		}
		return removeCnt;
	}

	public static void printMap() {
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < M; j++) {
				System.out.print(map[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	static class Point{
		public int r, c;
		public Point(int r, int c) {
			this.r = r;
			this.c = c;
		}
	}
}
