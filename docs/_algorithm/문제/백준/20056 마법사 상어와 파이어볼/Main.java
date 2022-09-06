import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main {

	/*
	 * 목적: 마법사 상어가 파이어볼의 이동을 K번 명령한 후 파이어볼 질량의 합
	 * 
	 * 조건:
	 * 1. 맵
	 * - NxN (4~50)
	 * - 1번 행은 N번과 연결되어 있고, 1번 열은 N번 열과 연결되어 있다. [주의]
	 * 2. 파이어볼
	 * - M개 (0~2500)
	 * - 위치: r, c (1~N, index: 0~N-1)
	 * - 질량: m (1~100)
	 * - 방향: d
	 * 7 0 1
	 * 6   2
	 * 5 4 3
	 * - 속력: s (1~1000)
	 * 
	 * 알고리즘:
	 * 1. 입력 시 맵에 파이어볼 개수 표기 및 파이어볼 리스트 초기화
	 * 2. K번 반복문
	 * 3. 파이어볼 리스트 탐색하면서 이동
	 *  1) d 방향으로 s 만큼 이동 (이동 중 한 칸에 여러 파이어볼 존재 가능)
	 *  2) 맵에 파이어볼 개수 갱신 (이동 함수를 만들어 이동 완료 위치 반환하기)
	 * 4. 2개 이상의 파이어볼이 있는 위치 정리 (모든 파이어볼 합쳐서 4개로 분리)
	 *  1) 질량: 질량 합 / 5
	 *   - 질량이 0 이라면: 해당 위치의 파이어볼 리스트에서 모두 제거, 맵에 파이어볼 개수 갱신(현재 위치의 개수 0)
	 *  2) 속력: 속력 합 / 파이어볼 개수
	 *  3) 방향: 모두 홀수 혹은 짝수라면 0,2,4,6 아니라면 1,3,5,7 [주의] 4 방향으로 파이어볼을 이동시키는 것이 아니라 현재 위치에 4개의 파이어볼을 생성만 하면된다.
	 *  4) 맵에 파이어볼 개수 갱신(현재 위치의 개수 4)
	 */
	
	static int N, M, K;
	static int[][] map;
	static ArrayList<Fireball> fireballs;
	static final int[] dr = {-1,-1,0,1,1,1,0,-1};
	static final int[] dc = {0,1,1,1,0,-1,-1,-1};
	static int res;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		map = new int[50][50];
		fireballs = new ArrayList<>();
		int r, c, m, d, s;
		for(int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			r = Integer.parseInt(st.nextToken())-1;
			c = Integer.parseInt(st.nextToken())-1;
			m = Integer.parseInt(st.nextToken());
			s = Integer.parseInt(st.nextToken());
			d = Integer.parseInt(st.nextToken());
			fireballs.add(new Fireball(new Point(r,c), m, d, s));
			map[r][c]++;
		}
		br.close();
		
		solution();
		System.out.println(res);
	}
	
	public static void solution() {
		for(int i = 0; i < K; i++) {
			move();
			reset();
		}
		
		for(Fireball f : fireballs) {
			res += f.m;
		}
	}
	
	public static void reset() {
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < N; j++) {
				if(map[i][j] > 1) {
					resetEx(i, j);
				}
			}
		}
	}
	
	public static void resetEx(int r, int c) {
		ArrayList<Fireball> sumList = new ArrayList<Main.Fireball>();
		int sumM = 0;
		int sumS = 0;
		int oddCnt = 0; // 홀수 개수
		int evenCnt = 0; // 짝수 개수
		
		for(Fireball fireball : fireballs) {
			if(fireball.pt.r == r && fireball.pt.c == c) {
				sumList.add(fireball);
				sumM += fireball.m;
				sumS += fireball.s;
				if(fireball.d % 2 == 0) evenCnt++;
				else oddCnt++;
			}
		}
		
		int newM = sumM/5;
		int newS = sumS/sumList.size();
		boolean allSame = false;
		if(oddCnt == sumList.size() || evenCnt == sumList.size()) allSame = true;
		
		if(newM != 0) {
			if(allSame) {
				for(int d = 0; d <= 6; d+=2) {
					fireballs.add(new Fireball(new Point(r, c), newM, d, newS));
				}
			}
			else {
				for(int d = 1; d <= 7; d+=2) {
					fireballs.add(new Fireball(new Point(r, c), newM, d, newS));
				}
			}
			
			map[r][c] = 4;
		}
		else {
			map[r][c] = 0;
		}
		
		for(Fireball fireball : sumList) {
			fireballs.remove(fireball);
		}
	}
	
	public static void move() {
		for(Fireball fireball : fireballs) {
			map[fireball.pt.r][fireball.pt.c]--;
			moveEx(fireball);
			map[fireball.pt.r][fireball.pt.c]++;
		}
	}
	
	public static void moveEx(Fireball fireball) {
		int cr = fireball.pt.r;
		int cc = fireball.pt.c;
		int nr, nc;
		for(int i = 0; i < fireball.s; i++) {
			nr = cr + dr[fireball.d];
			nc = cc + dc[fireball.d];
			
			if(!(nr >= 0 && nr < N && nc >= 0 && nc < N)) {
				if(nr < 0) nr = N-1;
				else if(nr >= N) nr = 0;
				if(nc < 0) nc = N-1;
				else if(nc >= N) nc = 0;
			}
			
			cr = nr;
			cc = nc;
		}
		
		fireball.pt.r = cr;
		fireball.pt.c = cc;
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
	static class Fireball{
		public Point pt;
		public int m;
		public int d;
		public int s;
		
		public Fireball(Point pt, int m, int d, int s) {
			this.pt = pt;
			this.m = m;
			this.d = d;
			this.s = s;
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
