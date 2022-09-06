import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

	/*
	 * 목적: 최대 5번 이동해서 만들 수 있는 가장 큰 블록 값
	 * 
	 * 조건:
	 * 1. 보드
	 * - NxN (1~20)
	 * - 0(빈칸), 2~1024 사이의 2의 제곱 꼴(블록)
	 * 2. 이동
	 * - 상하좌우
	 * 3. 합치기
	 * - 값이 같은 두 블록
	 * - 한 번의 이동에서 한 번 합쳐진다.
	 * - 이동하려는 쪽 먼저 합쳐진다.
	 * 
	 * 알고리즘:
	 * 1. 입력 단계에서 가장 큰 블록 값 초기화
	 * 2. DFS: 4방향, 이동 횟수
	 * - 5회 초과 시 종료
	 * 3. 블록 이동
	 * - 이동하는 쪽 블록 부터 이동 방향으로 이동
	 * - 합쳐질 수 있는지 확인: 같은 블록인가, 이미 합쳐진 블록은 아닌가
	 * - 합치기: 합쳐지는 블록 표시, 현재 가장 큰 블록과 비교 후 가장 큰 값 갱신
	 */
	static int N;
	static int[][] map;
	static final int[] dr = {-1,1,0,0};
	static final int[] dc = {0,0,-1,1};
	static int res;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		N = Integer.parseInt(br.readLine());
		map = new int[20][20];
		res = 0;
		for(int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j < N; j++) {
				int value = Integer.parseInt(st.nextToken());
				if(res < value) res = value;
				map[i][j] = value;
			}
		}
		br.close();
	
		solution();
		System.out.println(res);
	}

	public static void solution() {
		dfs(1);
	}
	
	public static void dfs(int cnt) {
		if(cnt > 5) {
			return;
		}
		
		int[][] copy = map.clone();
		for(int i = 0; i < N; i++) {
			copy[i] = map[i].clone();
		}
		
		for(int d = 0; d < 4; d++) {
			move(d);
			//printMap();
			
			dfs(cnt+1);
			
			map = copy.clone();
			for(int i = 0; i < N; i++) {
				map[i] = copy[i].clone();
			}
		}
	}
	
	public static void move(int dir) {
		// 한 번 이동에서 합쳐진 블록을 표시하기 위함
		boolean[][] check = new boolean[N][N];
		
		switch(dir) {
		case 0:
			for(int c = 0; c < N; c++) {
				for(int r = 0; r < N; r++) {
					if(map[r][c] == 0) continue;
					moveEx(dir, r, c, check);
				}
			}
			break;
		case 1:
			for(int c = 0; c < N; c++) {
				for(int r = N-1; r >= 0; r--) {
					if(map[r][c] == 0) continue;
					moveEx(dir, r, c, check);
				}
			}
			break;
		case 2:
			for(int r = 0; r < N; r++) {
				for(int c = 0; c < N; c++) {
					if(map[r][c] == 0) continue;
					moveEx(dir, r, c, check);
				}
			}
			break;
		case 3:
			for(int r = 0; r < N; r++) {
				for(int c = N-1; c >= 0; c--) {
					if(map[r][c] == 0) continue;
					moveEx(dir, r, c, check);
				}
			}
			break;
		}
	}
	
	public static void moveEx(int dir, int r, int c, boolean[][] check) {
		int nr, nc;
		while(true) {
			nr = r + dr[dir];
			nc = c + dc[dir];
			
			if(!(nr >= 0 && nr < N && nc >= 0 && nc < N)) break;
			// 앞에 다른 블록을 만난 경우
			if(map[nr][nc] != 0) {
				// 합쳐질 수 있는지 확인(이미 합쳐진 블록이 아니고 같은 값을 갖는 블록인가)
				if(check[nr][nc] == false && map[r][c] == map[nr][nc]) {
					map[nr][nc] *= 2;
					map[r][c] = 0;
					check[nr][nc] = true; // 합쳐진 블록 체크
					
					// 가장 큰 블록 값 갱신
					if(res < map[nr][nc]) res = map[nr][nc];
				}
				break;
			}
			
			// 이동
			map[nr][nc] = map[r][c];
			map[r][c] = 0;
			r = nr;
			c = nc;
		}
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
}
