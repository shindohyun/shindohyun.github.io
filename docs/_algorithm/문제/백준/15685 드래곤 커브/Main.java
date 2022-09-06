import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class Main {
	static final int MAP_SIZE = 101;
	static int N; // 1~20
	static int[][] map = new int[MAP_SIZE][MAP_SIZE]; // x,y 0~100
	static ArrayList<Dragon> dragons = new ArrayList<Dragon>();
	// 우상좌하
	static final int[] dr = {0,-1,0,1};
	static final int[] dc = {1,0,-1,0};
	static final int[] nextDir = {1, 2, 3, 0}; // 우:상:좌:하 = 0:1:2:3 에서 90도 회전 후 방향
	static final int[] searchDir = {0, 3, 2}; // 우하좌 현재 좌표에서 시계방향으로 돌면서 검사
	static int res = 0;
	
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		StringTokenizer st = null;
		for(int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());
			int d = Integer.parseInt(st.nextToken());
			int g = Integer.parseInt(st.nextToken());
			map[y][x] = 1;
			// 0세대 끝점 이동해서 저장
			x += dc[d];
			y += dr[d];
			dragons.add(new Dragon(x, y, d, g));
			map[y][x] = 1;
		}
		br.close();
		
		solution();
		System.out.println(res);
	}
	
	private static void solution() {
		for(Dragon dragon : dragons) {
			extend(dragon);
			//printMap();
		}
		search();
	}
	
	private static void search() {
		for(int i = 0; i < MAP_SIZE; i++) {
			for(int j = 0; j < MAP_SIZE; j++) {
				if(map[i][j] == 0) continue;
				
				int nr = i;
				int nc = j;
				
				boolean find = true;
				for(int d = 0; d < searchDir.length; d++) {
					nr += dr[searchDir[d]];
					nc += dc[searchDir[d]];
					
					if(!(nr >= 0 && nr < MAP_SIZE && nc >= 0 && nc < MAP_SIZE) || map[nr][nc] == 0) {
						find = false;
						break;
					}
				}
				if(find) res++;
			}
		}
	}
	
	private static void extend(Dragon dragon) {		
		// 각 드래곤의 세대 만큼 확장한다. 1세대 부터 시작
		for(int i = 1; i <= dragon.g; i++) {
			final int dCnt = dragon.ds.size();

			// 방향 정보를 최신 부터 과거 순으로 읽는다.
			for(int j = dCnt-1; j >= 0; j--) {
				int d = dragon.ds.get(j);
				
				// 시계방향 90도 회전 후 뱡항 얻기
				int nextD = nextDir[d];
				
				// 변경된 방향으로 끝 점 이동
				int nx = dragon.x + dc[nextD];
				int ny = dragon.y + dr[nextD];
				
				if(!(nx >= 0 && nx < MAP_SIZE && ny >= 0 && ny < MAP_SIZE)) continue;
				
				// 드래곤의 끝점을 이동하고 맵에 표시, 변경된 방향을 리스트 가장 뒤에 저장
				dragon.x = nx;
				dragon.y = ny;
				map[ny][nx] = 1;
				dragon.ds.addLast(nextD);				
			}						
		}
	}
	
	public static void printMap() {
		for(int i = 0; i < 20; i++) {
			for(int j = 0; j < 20; j++) {
				System.out.print(map[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}

	static class Dragon{
		public int x, y; // 0~100, 끝 점 저장
		public LinkedList<Integer> ds = new LinkedList<Integer>(); // 0~3, 최신 방향은 끝에 추가시킨다.
		public int g; // 0~10
		public Dragon() {}
		public Dragon(int x, int y, int d, int g) {
			this.x = x;
			this.y = y;
			this.g = g;
			ds.addLast(d);
		}
	}
}
