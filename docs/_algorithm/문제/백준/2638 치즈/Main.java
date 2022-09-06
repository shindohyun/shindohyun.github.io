import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	static int N, M; // 5~100
	static int[][] map = new int[100][100];
	static int[] dr = {-1,1,0,0};
	static int[] dc = {0,0,-1,1};
	static int cnt = 0;
	static int res = 0;
	
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
				if(input == 1) cnt++;
			}
		}
		br.close();
		
		solution();
		System.out.println(res);
	}

	public static void solution() {
		while(true) {
			res++;
			bfs();
//			printMap();
			int removeCnt = remove();
			
			if(cnt - removeCnt <= 0) {
				break;
			}
			else {
				cnt -= removeCnt;
			}
		}
	}
	public static void bfs() {
		Queue<Point> q = new LinkedList<Main.Point>();
		boolean[][] visit = new boolean[N][M];
		
		q.add(new Point(0,0));
		visit[0][0] = true;
		
		while(!q.isEmpty()) {
			Point cur = q.poll();
			
			int nr, nc;
			for(int d = 0; d < 4; d++) {
				nr = cur.r + dr[d];
				nc = cur.c + dc[d];
				
				if(!(nr >= 0 && nr < N && nc >= 0 && nc < M) || visit[nr][nc]) continue;
				
				if(map[nr][nc] >= 1) {
					map[nr][nc]++;
					continue;
				}
				
				visit[nr][nc] = true;
				q.add(new Point(nr, nc));
			}
		}
		
	}
	
	public static int remove() {
		int removeCnt = 0;
		for(int r = 0; r < N; r++) {
			for(int c = 0; c < M; c++) {
				if(map[r][c] >= 3) {
					removeCnt++;
					map[r][c] = 0;
				}
				else if(map[r][c] != 0){
					map[r][c] = 1;
				}
			}
		}
		return removeCnt;
	}
	
	public static void printMap() {
		for(int r = 0; r < N; r++) {
			for(int c = 0; c < M; c++) {
				System.out.print(map[r][c] + " ");
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
