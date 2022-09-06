import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	/*
	 * 목적: 최소 몇 번 만에 빨간 구슬을 빼낼 수 있는가?
	 * 
	 * 조건:
	 * 1. 보드
	 * - MxN (3~10)
	 * - .(빈칸), #(벽), O(구멍), R(빨간 구슬), B(파란 구슬)
	 * - 가장자리는 막혀있음(#)
	 * - 구멍, 빨간 구슬, 파란 구슬은 하나씩
	 * 2. 굴리기
	 * - 상하좌우
	 * - 더이상 구슬이 움직이지 않을 때 까지
	 * - 10번 초과 시 실패(-1 반환)
	 * - 빨간 구슬, 파란 구슬 동시에 움직임
	 * - 파란 구슬은 구멍에 빠지면 안됨
	 * 
	 * 알고리즘:
	 * 1. 이동 횟수 초기값 -1
	 * 2. DFS: 4방향, 이동 횟수
	 * - 10번 초과 시 실패 종료
	 * 3. 구슬 이동
	 * 4. 조건 확인:
	 * - 구슬이 움직였는가? N: 종료, Y: 다음
	 * - 파란 구슬이 빠졌는가? N: 다음, Y: 종료
	 */
	
	static int N; //세로
	static int M; //가로
	static char[][] map;
	static Point red;
	static Point blue;
	static final int[] dr = {-1,1,0,0};
	static final int[] dc = {0,0,-1,1};
	static int res;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		map = new char[10][10];
		for(int i = 0; i < N; i++) {
			String temp = br.readLine();
			for(int j = 0; j < M; j++) {
				char tok = temp.charAt(j);
				
				if(tok == '#' || tok == 'O' || tok == '.') {
					map[i][j] = tok;
				}
				else if(tok == 'R') {
					red = new Point(i,j);
					map[i][j] = '.';
				}
				else if(tok == 'B') {
					blue = new Point(i,j);
					map[i][j] = '.';
				}
			}
		}
		res = -1;
		
		br.close();
		
		solution();
		System.out.println(res);
	}

	public static void solution() {
		dfs(1);
	}
	
	public static void dfs(int cnt) {
		if(res != -1 && res < cnt) return;
		if(cnt > 10) return;
		
		for(int d = 0; d < 4; d++) {
			Point redPre = new Point(red.r, red.c);
			Point bluePre = new Point(blue.r, blue.c);
			
			int moveRes = move(d, redPre, bluePre);
			//printMap();

			if(moveRes == 1) {
				if(res == -1 || res > cnt) res = cnt;
				break;
			}
			else if(moveRes == 0) {
				dfs(cnt+1);
			}
			else if(moveRes == -1) {
				
			}
			
			red = redPre;
			blue = bluePre;
		}
	}
	
	/**
	 * @return 
	 * -1: 파란 구슬이 빠짐 또는 구슬을 이동할 수 없음
	 * 0: 구슬을 이동했음
	 * 1: 빨간 구슬만 빠짐
	 */
	public static int move(int dir, final Point redPre, final Point bluePre) {
		boolean redFirst = true;
		
		switch(dir) {
		case 0:
			if(red.r > blue.r) redFirst = false;
			break;
		case 1:
			if(red.r < blue.r) redFirst = false;
			break;
		case 2:
			if(red.c > blue.c) redFirst = false;
			break;
		case 3:
			if(red.c < blue.c) redFirst = false;
			break;
		}
		
		char redRes, blueRes;
		if(redFirst) {
			redRes = moveEx(red, blue, dir);
			blueRes = moveEx(blue, red, dir);
		}
		else {
			blueRes = moveEx(blue, red, dir);
			redRes = moveEx(red, blue, dir);
		}
		
		// -1 인 경우 체크
		if((redPre.r == red.r && redPre.c == red.c && bluePre.r == blue.r && bluePre.c == blue.c) ||
				blueRes == 'O') {
			return -1;
		}
		// 1인 경우 체크
		else if(redRes == 'O') {
			return 1;
		}
		else {
			return 0;
		}
	}
	
	public static char moveEx(Point ball, Point other, int dir) {
		int nr, nc;
		
		while(true) {
			nr = ball.r + dr[dir];
			nc = ball.c + dc[dir];
			
			// 구멍에 빠지는 경우 현재 위치 저장
			if(map[nr][nc] == 'O') {
				ball.r = nr;
				ball.c = nc;
				break;
			}
			
			// 벽이나 다른 공을 만나는 경우 현재 위치 저장 없이 종료
			if(map[nr][nc] == '#' || (nr == other.r && nc == other.c)) break;
			
			ball.r = nr;
			ball.c = nc;
		}
		
		return map[ball.r][ball.c];
	}
	
	public static void printMap() {
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < M; j++) {
				if(red.r == i && red.c == j) {
					System.out.print('R');
				}
				else if(blue.r == i && blue.c == j) {
					System.out.print('B');
				}
				else {
					System.out.print(map[i][j]);
				}
			}
			System.out.println();
		}
		System.out.println();
	}
	
	public static class Point {
		public int r, c;
		public Point(int r, int c) {
			this.r = r;
			this.c = c;
		}
	}
}
