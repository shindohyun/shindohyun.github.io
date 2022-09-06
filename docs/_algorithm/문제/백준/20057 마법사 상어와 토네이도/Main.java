import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

	/*
	 * 목적: 맵 밖으로 나가는 모래의 양
	 * 
	 * 조건: 
	 * 1. 맵
	 * - NxN 3~499(홀수)
	 * - A[r][c]: 해당 위치의 모래 양 (0~1,000), 가운데는 0
	 * 2. 토네이도 이동
	 * - 맵의 가운데에서 부터 시작
	 * - 한 번에 한 칸 이동
	 * - 한 번 이동 시 이동하는 위치의 모래가 흩날린다.
	 * - (1,1)까지 이동 후 소멸
	 * 3. 모래 흩날리기
	 * - 비율과 a(알파)가 적힌 칸으로 흩날림, 이동 위치의 모래에 더해진다. 
	 * - 비율: 이동하는 위치의 모래에 대한 해당 비율 만큼
	 * - a(알파): 비율이 적힌 칸으로 흩날리고 남은 모래
	 * 
	 * 알고리즘:
	 * 1. 맵의 가운데(시작) 위치 찾기 
	 * 2. 토네이도 이동(루프)
	 * - 이동
	 * - 흩날리기
	 * - 현재 위치가 (1,1)이면 종료
	 * 3. 모래 흩날리기
	 * - 비율과 a(알파) 위치 및 %를 순서대로 지정
	 * - 순서대로 탐색하면서 이동하는 위치에서 모래를 제거하고 흩날려지는 위치에 더해준다.
	 * - 흩날려지는 위치가 맵을 벗어난 경우는 결과값에 저장한다.
	 */
	
	static int N;
	static int[][] map = new int[499][499];
	static boolean[][] visit = new boolean[499][499]; //토네이도 이동 경로 표시
	static Tornado t;
	// 토네이도 이동 순서 (좌하우상)
	static final int[] dr = {0,1,0,-1};
	static final int[] dc = {-1,0,1,0};
	static int res;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		N = Integer.parseInt(br.readLine());
		for(int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		t = new Tornado(new Point(N/2, N/2), 0);
		visit[t.pt.r][t.pt.c] = true;
		br.close();
		
		solution();
		System.out.println(res);
	}
	
	public static void solution() {
		while(!(t.pt.r == 0 && t.pt.c ==0)) {
			while(true) {
				if(move()) {
					flutter();
					
					t.d++;
					if(t.d > 3) t.d = 0;
					break;
				}
				else {
					t.d--;
					if(t.d < 0) t.d = 3;
				}
			}
		}
	}
	
	public static boolean move() {
		int nr, nc;
		nr = t.pt.r + dr[t.d];
		nc = t.pt.c + dc[t.d];
		
		if(visit[nr][nc]) {
			return false;
		}
		else {
			t.pt.r = nr;
			t.pt.c = nc;
			visit[t.pt.r][t.pt.c] = true;
			return true;
		}
	}
	
	public static void flutter() {
		// 토네이도 방향에 따른 흩날림 위치
		/* index
		      1
		    2 3 4
		  5 10
		    6 7 8
		     9
		 */
		/* percent
		      2
		   10 7 1
		 5 a
		   10 7 1
		      2
		 */
		/* 
		 					[(d+3)%4,(d+3)%4]
		        [d,(d+3)%4] [(d+3)%4] [(d+2)%4,(d+3)%4]
		 [d,d]  [d]                   
		        [d,(d+1)%4] [(d+1)%4] [(d+2)%4,(d+1)%4]
		                    [(d+1)%4,(d+1)%4]
		 */
		//				
		int nr, nc, nd, sum = 0;
		//1
		nd = (t.d+3)%4;
		nr = t.pt.r + dr[nd];
		nc = t.pt.c + dc[nd];
		nr = nr + dr[nd];
		nc = nc + dc[nd];
		sum += flutterEx(nr, nc, 2);
		//2
		nd = t.d;
		nr = t.pt.r + dr[nd];
		nc = t.pt.c + dc[nd];
		nd = (t.d+3)%4;
		nr = nr + dr[nd];
		nc = nc + dc[nd];
		sum += flutterEx(nr, nc, 10);
		//3
		nd = (t.d+3)%4;
		nr = t.pt.r + dr[nd];
		nc = t.pt.c + dc[nd];
		sum += flutterEx(nr, nc, 7);
		//4
		nd = (t.d+2)%4;
		nr = t.pt.r + dr[nd];
		nc = t.pt.c + dc[nd];
		nd = (t.d+3)%4;
		nr = nr + dr[nd];
		nc = nc + dc[nd];
		sum += flutterEx(nr, nc, 1);
		//5
		nd = t.d;
		nr = t.pt.r + dr[nd];
		nc = t.pt.c + dc[nd];
		nd = t.d;
		nr = nr + dr[nd];
		nc = nc + dc[nd];
		sum += flutterEx(nr, nc, 5);
		//6
		nd = t.d;
		nr = t.pt.r + dr[nd];
		nc = t.pt.c + dc[nd];
		nd = (t.d+1)%4;
		nr = nr + dr[nd];
		nc = nc + dc[nd];
		sum += flutterEx(nr, nc, 10);
		//7
		nd = (t.d+1)%4;
		nr = t.pt.r + dr[nd];
		nc = t.pt.c + dc[nd];
		sum += flutterEx(nr, nc, 7);
		//8
		nd = (t.d+2)%4;
		nr = t.pt.r + dr[nd];
		nc = t.pt.c + dc[nd];
		nd = (t.d+1)%4;
		nr = nr + dr[nd];
		nc = nc + dc[nd];
		sum += flutterEx(nr, nc, 1);
		//9
		nd = (t.d+1)%4;
		nr = t.pt.r + dr[nd];
		nc = t.pt.c + dc[nd];
		nd = (t.d+1)%4;
		nr = nr + dr[nd];
		nc = nc + dc[nd];
		sum += flutterEx(nr, nc, 2);
		
		//10
		nd = t.d;
		nr = t.pt.r + dr[nd];
		nc = t.pt.c + dc[nd];
		if(!(nr >= 0 && nr < N && nc >= 0 && nc < N)) {
			res += (map[t.pt.r][t.pt.c] - sum);
		}
		else {
			map[nr][nc] += (map[t.pt.r][t.pt.c] - sum);
		}
		
		// y
		map[t.pt.r][t.pt.c] = 0;
		//printMap();
	}
	public static int flutterEx(int r, int c, int per) {
		int value = map[t.pt.r][t.pt.c] * per / 100;
		
		if(!(r >= 0 && r < N && c >= 0 && c < N)) {
			res += value;
		}
		else {
			map[r][c] += value;
		}
		
		return value;
	}
	public static void printMap() {
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < N; j++) {
				System.out.print(map[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
	public static void printVisit() {
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < N; j++) {
				System.out.print(visit[i][j] ? "1 " : "0 ");
			}
			System.out.println();
		}
		System.out.println();
	}
	static class Tornado{
		public Point pt;
		public int d; //0:1:2:3 = 좌:하:우:상
		public Tornado(Point pt, int d) {
			this.pt = pt;
			this.d = d;
		}
	}
	
	static class Point{
		public int r, c;
		public Point(int r, int c) {
			this.r = r;
			this.c = c;
		}
	}
}
