import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	static int N, M; // 2~50
	static int T; // 1~50
	static int[][] map = new int[50][50];
	static int remain;
	static int res = 0;
	static int[] dr = {-1,1,0,0};
	static int[] dc = {0,0,-1,1};
	
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		T = Integer.parseInt(st.nextToken());
		for(int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j < M; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		remain = N*M; // 원판에 있는 숫자 개수
		for(int i = 0; i < T; i++) {
			st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken());
			int d = Integer.parseInt(st.nextToken());
			int k = Integer.parseInt(st.nextToken());
			solution(x,d,k);
		}
		br.close();
		
		calculate();
		System.out.println(res);
	}
	
	public static void solution(int x, int d, int k) {
		int cnt = 1;
		int curK;
		
		// 회전
		while(x*cnt <= N) {
			int panelIdx = x*cnt-1;
			int[] panel = new int[M];
			
			curK = k;
			while(curK-- > 0) {
				if(d == 0) {
					int temp = map[panelIdx][M-1];
					for(int i = 0; i < M-1; i++) {
						panel[i+1] = map[panelIdx][i];
					}
					panel[0] = temp;
					
					map[panelIdx] = panel.clone();
				}
				else {
					int temp = map[panelIdx][0];
					for(int i = M-1; i > 0; i--) {
						panel[i-1] = map[panelIdx][i];
					}
					panel[M-1] = temp;
					
					map[panelIdx] = panel.clone();
				}
			}
			cnt++;
		}
		
		// 원판에 수가 남아있는 경우
		if(remain > 0) {
			// 인접하면서 수가 같은 곳 찾기
			int sum = 0;
			int sumCnt = 0;
			boolean found = false;
			boolean[][] visit = new boolean[N][M];
			
			for(int r = 0; r < N; r++) {
				for(int c = 0; c < M; c++) {
					if(map[r][c] == 0) continue;
					
					if(!found) {
						// 인접하면서 수가 같은 곳이 한 곳도 없는 경우 평균을 내기 위해 준비
						sumCnt++;
						sum += map[r][c];
					}
					
					for(int dir = 0; dir < 4; dir++) {
						int nr = r + dr[dir];
						int nc = c + dc[dir];
						
						if(!(nr >= 0 && nr < N && nc >= 0 && nc < M)) {
							// 인접한 수를 볼 때 다른 원판 끼리(행)는 배열 범위 내에서, 
							// 동일 원판 내에서(열)는 배열 범위 넘어가는 경우 반대의 양 끝으로 이동
							if(dir == 2) {
								nc = M-1;
							}
							else if(dir == 3) {
								nc = 0;
							}
							else continue;
						}
						
						if(map[r][c] == map[nr][nc]) {
							visit[r][c] = true;
							visit[nr][nc] = true;
							if(!found) found = true;
						}
					}
				}
			}
			
			// 인접하면서 수가 같은 곳이 한 곳도 없는 경우 평균내기
			if(!found) {
				int avr = sum / sumCnt;
				int rest = sum % sumCnt;
				
				for(int r = 0; r < N; r++) {
					for(int c = 0; c < M; c++) {
						if(map[r][c] == 0) continue;
						
						if(map[r][c] > avr) map[r][c]--;
						else if(map[r][c] < avr) map[r][c]++;
						else {
							if(rest > 0) map[r][c]++; // 주의!) 평균이 실수인 경우
						}
					}
				}
			}
			else {
				for(int r = 0; r < N; r++) {
					for(int c = 0; c < M; c++) {
						if(visit[r][c]) {
							map[r][c] = 0;
							remain--;
						}
					}
				}
			}
		}
	}
	
	public static void calculate() {
		for(int r = 0; r < N; r++) {
			for(int c = 0; c < M; c++) {
				res += map[r][c];
			}
		}
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
}
