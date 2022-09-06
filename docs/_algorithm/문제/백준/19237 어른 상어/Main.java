import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
	static class Point{
		public int r, c;
		public Point(int r, int c) {
			this.r = r;
			this.c = c;
		}
	}
	static class Shark implements Comparable<Shark>{
		public int idx; // 1~400
		public int r, c, d;
		public int[][] order = new int[4][4];
		
		public Shark(int idx, int r, int c) {
			this.idx = idx;
			this.r = r;
			this.c = c;
		}

		@Override
		public int compareTo(Shark o) {
			return this.idx - o.idx;
		}
	}
	
	static class Smell implements Comparable<Smell>{
		public int idx;
		public int time; // 남은 시간
		public Smell(int idx, int time) {
			this.idx = idx;
			this.time = time;
		}
		@Override
		public int compareTo(Smell o) {
			return this.idx - o.idx;
		}
	}
	
	static int N; // 2~20
	static int M;
	static int K; // 1~1000
	static Smell[][] map = new Smell[20][20];
	static ArrayList<Shark> sharks = new ArrayList<Main.Shark>();
	static int res = 0;
	static final int[] dr = {-1,1,0,0};
	static final int[] dc = {0,0,-1,1};
	
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		for(int r = 0; r < N; r++) {
			st = new StringTokenizer(br.readLine());
			for(int c = 0; c < N; c++) {
				int idx = Integer.parseInt(st.nextToken());
				
				// 냄새 유지 시간을 먼저 줄이고 이동시켜야 구분이 되기 때문에 +1을 해서 넣어준다.
				if(idx != 0) {
					Shark s = new Shark(idx, r, c);
					sharks.add(s);
					map[r][c] = new Smell(idx, K+1);
				}
				else map[r][c] = new Smell(0, K+1);
			}
		}
		Collections.sort(sharks);
		st = new StringTokenizer(br.readLine());
		for(Shark s : sharks){
			s.d = Integer.parseInt(st.nextToken())-1;
		}
		for(Shark s : sharks) {
			for(int i = 0; i < 4; i++) {
				st = new StringTokenizer(br.readLine());
				for(int j = 0; j < 4; j++) {
					s.order[i][j] = Integer.parseInt(st.nextToken())-1;
				}
			}
		}
		br.close();
		
		solution();
		System.out.println(res);
	}
	
	public static void solution() {
		while(true) {
			if(sharks.size() == 1) break;
			
			// 현재 맵의 냄새 시간을 줄인다.
			removeSmell();
						
			//printMap();
			
			// 이동
			move();
			
			res++;
			
			if(res > 1000) {
				res = -1;
				break;
			}
		}
	}
	
	public static void removeSmell() {
		for(int r = 0; r < N; r++) {
			for(int c = 0; c < N; c++) {
				if(map[r][c].idx == 0) continue;
				
				map[r][c].time--;
				if(map[r][c].time == 0) {
					map[r][c].idx = 0;
				}
			}
		}
	}
	
	public static void move() {
		PriorityQueue<Smell>[][] tempMap = new PriorityQueue[N][N];
		
		for(Shark s : sharks) {
			Point empty = null; // 빈 곳
			int emptyDir = 0;
			Point mine = null; // 자신의 냄새가 있는 곳
			int mineDir = 0;
			
			int[] order = s.order[s.d]; // 현재 방향에 대한 우선순위
			int nr, nc;
			for(int o = 0; o < 4; o++) {
				nr = s.r + dr[order[o]];
				nc = s.c + dc[order[o]];
				
				if(!(nr >= 0 && nr < N && nc >= 0 && nc < N)) continue;
				
				if(empty == null && map[nr][nc].idx == 0) {
					empty = new Point(nr,nc);
					emptyDir = order[o];
					break; // 빈곳을 찾으면 탐색 종료
				}
				else if(mine == null && map[nr][nc].idx == s.idx) {
					mine = new Point(nr, nc);
					mineDir = order[o];
				}
			}
			
			// 이동한 공간으로 이동시키고 이동 방향과 위치를 갱신
			if(empty != null) {
				if(tempMap[empty.r][empty.c] == null) {
					tempMap[empty.r][empty.c] = new PriorityQueue<Main.Smell>();
				}
				tempMap[empty.r][empty.c].add(new Smell(s.idx, K+1)); 
				s.d = emptyDir;
				s.r = empty.r;
				s.c = empty.c;
			}
			else {
				if(tempMap[mine.r][mine.c] == null) {
					tempMap[mine.r][mine.c] = new PriorityQueue<Main.Smell>();
				}
				tempMap[mine.r][mine.c].add(new Smell(s.idx, K+1));
				s.d = mineDir;
				s.r = mine.r;
				s.c = mine.c;
			}
		}
		
		// 모든 상어가 이동을 마친 뒤 번호가 가장 낮은 상어만 남기고 맵을 갱신한다.
		for(int r = 0; r < N; r++) {
			for(int c = 0; c < N; c++) {
				if(tempMap[r][c] == null) continue;
				Smell smell = tempMap[r][c].poll();
				map[r][c] = smell;
				
				while(!tempMap[r][c].isEmpty()) {
					// 쫓겨나는 상어를 제거한다.
					Smell removeSmell = tempMap[r][c].poll();
					for(Shark s : sharks) {
						if(s.idx == removeSmell.idx) {
							sharks.remove(s);
							break;
						}
					}
				}
			}
		}
	}
	
	public static void printMap() {
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < N; j++) {
				if(map[i][j].idx == 0)
					System.out.print("0,0 ");
				else 
					System.out.print(map[i][j].idx +","+ map[i][j].time + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
}
