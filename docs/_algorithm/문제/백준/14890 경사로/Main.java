import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static int N; // 2~100
	static int L; // 1~100
	static int[][] map = new int[100][100];
	static int res = 0;
	
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		L = Integer.parseInt(st.nextToken());
		for(int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		br.close();
		
		solution();
		System.out.println(res);
	}
	
	public static void solution() {
		int[][] rowCheck = map.clone();
		for(int r = 0; r < N; r++) {
			rowCheck[r] = map[r].clone();
		}
		res+=simulate(rowCheck);
		
		int[][] colCheck = new int[N][N];
		// 90도 회전
		for(int r = 0; r < N; r++) {
			for(int c = 0; c < N; c++) {
				colCheck[c][N-1-r] = map[r][c];
			}
		}
		res+=simulate(colCheck);
		
	}
	
	public static int simulate(int[][] m) {
		int cnt = 0;
		
		boolean[][] visit = new boolean[N][N];
		
		for(int r = 0; r < N; r++) {
			int c = 0;
			int nc;
			while(c < N) {
				nc = c+1;
				
				if(nc >= N) break;
				if(Math.abs(m[r][c] - m[r][nc]) > 1) break;
				
				// 앞이 낮음
				if(m[r][c] < m[r][nc]) {
					// 현 위치에서 뒤로 경사로 놓을 수 있는지 확인
					boolean able = true;
					int bHight = m[r][c];
					int bc = c;
					for(int i = 0; i < L; i++) {
						// 동일한 높이, 가용범위, 다른 사다리
						if(bc < 0 || m[r][bc] != bHight || visit[r][bc]) {
							able =false;
							break;
						}
						bc--;
					}
					
					if(able) {
						// 사다리 체크
						bc = c;
						for(int i = 0; i < L; i++) {
							visit[r][bc] = true;
							bc--;
						}
					}
					else {
						break;
					}
				}
				// 앞이 높음
				else if(m[r][c] > m[r][nc]) {
					// 현 위치 앞에서 부터 앞으로 경사로 놓을 수 있는지 확인
					boolean able = true;
					int bHight = m[r][nc];
					int bc = nc;
					for(int i = 0; i < L; i++) {
						// 동일한 높이, 가용범위, 다른 사다리
						if(bc >= N || m[r][bc] != bHight || visit[r][bc]) {
							able =false;
							break;
						}
						bc++;
					}
					
					if(able) {
						// 사다리 체크
						bc = nc;
						for(int i = 0; i < L; i++) {
							visit[r][bc] = true;
							bc++;
						}
						// 앞으로 놓인 사다리 앞으로 현재 위치를 이동
						c = bc+1;
					}
					else {
						break;
					}
				}
				c = nc;
			}
			
			// 끝까지 탐색을 마쳤다면 이동 가능한 경로로 판단
			if(c == N-1) cnt++;
		}
		return cnt;
	}
}