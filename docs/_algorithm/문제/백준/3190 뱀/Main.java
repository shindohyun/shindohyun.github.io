import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

	/*
	 * 목적: 게임이 끝나는 시간(초) 구하기
	 * 
	 * 조건:
	 * 1. 보드
	 * - NxN (2~100)
	 * - 상하좌우 끝에 벽
	 * - K개의 사과 (0~100)
	 * 2. 뱀
	 * - 시작 위치: 맨 위, 맨 좌측
	 * - 시작 길이: 1
	 * - 시작 방향: 오른쪽
	 * - L번의 뱀의 방향 변환 (1~100)
	 * - 게임 시작 시간으로부터 X초 끝난 뒤 C방향으로 90도 회전
	 * - C: L(왼), D(오)
	 * 3. 이동
	 * - 매 초 마다 이동
	 * - 이동하다가 사과를 먹으면 몸 길이가 늘어남
	 * - 순서:
	 *  1) 몸 길이 늘려 머리를 다음 칸에 위치
	 *  2) 이동한 위치에 사과가 있다면 꼬리 고정, 없다면 꼬리 전진(현재 꼬리 위치 제거)
	 * 4. 종료
	 * - 벽 또는 자기 자신과 부딪히는 경우
	 * 
	 * 알고리즘:
	 * 1. while 반복문
	 * 2. 뱀 이동 (머리-꼬리 순서)
	 * 3. 종료 조건으로 반복문 탈출
	 * 4. 뱀 방향 회전
	 */
	static int N;
	static int[][] map = new int[100][100];
	static int K;
	static int L;
	static Queue<Turn> turns = new LinkedList<>();
	static Deque<Point> snake = new LinkedList<>();
	static int d; // 0,1,2,3 : 상,하,좌,우
	static final int[] dr = {-1,1,0,0};
	static final int[] dc = {0,0,-1,1};
	static int time;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		N = Integer.parseInt(br.readLine());
		K = Integer.parseInt(br.readLine());
		int r, c;
		for(int i = 0; i < K; i++) {
			st = new StringTokenizer(br.readLine());
			r = Integer.parseInt(st.nextToken())-1;
			c = Integer.parseInt(st.nextToken())-1;
			map[r][c] = 2;
		}
		L = Integer.parseInt(br.readLine());
		int x;
		char ch;
		for(int i = 0; i < L; i++) {
			st = new StringTokenizer(br.readLine());
			x = Integer.parseInt(st.nextToken());
			ch = st.nextToken().toCharArray()[0];
			turns.add(new Turn(x, ch));
		}
		br.close();
		
		solution();
		System.out.println(time);
	}
	
	public static void solution() {
		time = 1;
		moveSnakeHead(new Point(0,0));
		d = 3;
		
		while(true) {
			if(!move(d)) break;
			//printMap();
			
			d = nextTurn(time, d);
			time++;
		}
	}
	
	public static boolean move(int dir) {
		Point head = snake.getFirst();
		
		// 이동할 위치 검사 (벽이거나 자기 자신이면 종료)
		int nr, nc;
		nr = head.r + dr[dir];
		nc = head.c + dc[dir];
		if(!(nr >= 0 && nr < N && nc >= 0 && nc < N) || map[nr][nc] == 3) return false;
		
		Point newHead = new Point(nr, nc);
		
		// 사과 검사
		boolean apple = false;
		if(map[newHead.r][newHead.c] == 2) {
			apple = true;
		}
		
		moveSnakeHead(newHead);
		if(!apple) {
			moveSnakeTail();
		}
				
		return true;
	}
	
	public static int nextTurn(int cur, int dir) {
		if(turns.size() == 0) return dir;
		if(turns.peek().x != cur) return dir;
		
		Turn turn = turns.poll();
		
		if(turn.c == 'L') {
			switch(dir) {
			case 0: return 2;
			case 1: return 3;
			case 2: return 1;
			case 3: return 0;
			}
		}
		else {
			switch(dir) {
			case 0: return 3;
			case 1: return 2;
			case 2: return 0;
			case 3: return 1;
			}
		}
		
		return dir;
	}
	
	public static void moveSnakeHead(Point pt) {
		snake.addFirst(pt);
		map[pt.r][pt.c] = 3;
	}
	
	public static void moveSnakeTail() {
		Point tail = snake.getLast();
		snake.removeLast();
		map[tail.r][tail.c] = 0;
	}
	
	public static void printMap() {
		for(int i = 0; i < N; i++){
			for(int j = 0; j < N; j++) {
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
	
	static class Turn{
		public int x;
		public char c;
		public Turn(int x, char c) {
			this.x = x;
			this.c = c;
		}
	}
}
