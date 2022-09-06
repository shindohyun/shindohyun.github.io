import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	/*
	 * 목적: 남아있는 얼음 양의 합, 가장 큰 얼음 덩어리가 차지하는 칸의 개수 (얼음 덩어리가 없으면 0 출력)
	 * 
	 * 조건:
	 * 1. 맵
	 * - 2^Nx2^N (2~6)
	 * - A[r][c]: 해당 위치의 얼음 양 (0: 얼음 없음)
	 * 2. 파이어스톰 시전
	 * - 시전 횟수: Q (1~1,000)
	 * - 시전
	 *  1) 맵을 2^Lx2^L 크기로 나눈다. (0~6)
	 *  2) 나눠진 모든 부분 맵들은 시계방향으로 90도 회전시킨다.
	 *  3) 얼음이 3개 이상의 다른 얼음과 상하좌우로 인접해 있지 않은 경우 얼음 양 -1
	 *   - [주의] 다른 얼음과 인접하지 않은 얼음 위치를 모두 저장했다가 한 번에 처리해야한다.
	 * 
	 * 알고리즘:
	 * 1. 반복문 (Q만큼)
	 * 2. 시전
	 *  1) 맵 나누기
	 *  2) 나눠진 맵 시계방향으로 90도 회전 
	 *  3) 인접하지 않은 얼음 -1
	 * 3. 전체 얼음 양 합
	 * 4. 가장 큰 얼음 덩어리의 크기 (BFS)
	 */
	static int N, Q;
	static int mapSize;
	static int[][] map = new int[64][64];
	static boolean[][] visit;
	static int[] Ls = new int[1000];
	static final int[] dr = {-1,1,0,0};
	static final int[] dc = {0,0,-1,1};
	static int sum;
	static int max;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		mapSize = (int) Math.pow(2, N);
		Q = Integer.parseInt(st.nextToken());
		for(int i = 0; i < mapSize; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j < mapSize; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		st = new StringTokenizer(br.readLine());
		for(int i = 0; i < Q; i++) {
			Ls[i] = Integer.parseInt(st.nextToken());
		}
		br.close();
		
		sum = 0;
		max = 0;
		
		solution();
		System.out.println(sum);
		System.out.println(max);
	}
	
	public static void solution() {
		int L;
		for(int i = 0; i < Q; i++) {
			L = Ls[i];
			visit = new boolean[64][64];
			divMap((int)Math.pow(2, L));
			removeIce();
			//printMap();
		}
		
		calculateSumAndMax();
	}
	
	public static void divMap(int size) {
		int r = 0;
		int c = 0;
		int[][] newMap = new int[64][64];
		while(true) {
			rotateMap(r, c, size, newMap);
			
			c += size;
			if(!(c >= 0 && c < mapSize)) {
				c = 0;
				r += size;
				if(!(r >= 0 && r < mapSize)) {
					break;
				}
			}
		}
		map = newMap;
	}
	
	public static void rotateMap(int r, int c, int size, int[][] newMap) {		
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				newMap[j+r][size-1-i+c] = map[i+r][j+c];
			}
		}
	}
	
	public static void removeIce() {
		boolean[][] removeCheck = new boolean[64][64];
		
		for(int i = 0; i < mapSize; i++) {
			for(int j = 0; j < mapSize; j++) {
				if(map[i][j] == 0) continue;
				
				int cnt = 0;
				int nr, nc;
				for(int d = 0; d < 4; d++) {
					nr = i + dr[d];
					nc = j + dc[d];
					if(!(nr >= 0 && nr < mapSize && nc >= 0 && nc < mapSize)) continue;
					if(map[nr][nc] > 0) cnt++;
				}
				
				if(cnt < 3) {
					removeCheck[i][j] = true;
				}
			}
		}
		
		for(int i = 0; i < mapSize; i++) {
			for(int j = 0; j < mapSize; j++) {
				if(removeCheck[i][j]) map[i][j]--;
			}
		}
	}
	
	public static void calculateSumAndMax() {
		for(int i = 0; i < mapSize; i++) {
			for(int j = 0; j < mapSize; j++) {
				if(map[i][j] != 0) {
					if(visit[i][j] == false) {
						int cnt = calculateMax(i, j);
						if(cnt >= 2 && max < cnt) max = cnt;
					}
					sum += map[i][j];
				}
			}
		}
	}
	
	public static int calculateMax(int r, int c) {
		Queue<Point> q = new LinkedList<>();
		q.add(new Point(r,c));
		visit[r][c] = true;

		int cnt = 0;
		Point cur;
		
		while(!q.isEmpty()) {
			cur = q.poll();
			cnt++;
			
			int nr, nc;
			for(int d = 0; d < 4; d++) {
				nr = cur.r + dr[d];
				nc = cur.c + dc[d];
				
				if(!(nr >= 0 && nr < mapSize && nc >= 0 && nc < mapSize)) continue;
				if(map[nr][nc] == 0) continue;
				if(visit[nr][nc] == true) continue;
				
				q.add(new Point(nr, nc));
				visit[nr][nc] = true;
			}
		}
		
		return cnt;
	}
	
	public static void printMap() {
		for(int i = 0; i < mapSize; i++) {
			for(int j = 0; j < mapSize; j++) {
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
