import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	
	static class Point{
		public int r,c;
		public Point(int r, int c) {
			this.r = r;
			this.c = c;
		}
	}
	
	static int N, M; //3~8
	static int[][] map = new int[8][8];
	static int max = Integer.MIN_VALUE;
	static int[] dr= {-1,1,0,0};
	static int[] dc= {0,0,-1,1};
	static int emptyCnt = 0;
	
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		int input;
		for(int i=0; i<N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0; j<M; j++) {
				input = Integer.parseInt(st.nextToken());
				map[i][j] = input;
				if(input==0)emptyCnt++;
			}
		}
		br.close();
		
		solution();
		System.out.println(max);
	}
	
	private static void solution() {
		recursive(0,0,0);
	}
	
	private static void recursive(int cnt, int cr, int cc) {
		if(cnt == 3) {
			bfs();
			return;
		}
		
		if(cnt > 3) return;
		
		
		for(int r=cr; r<N; r++) {
			for(int c=cc; c<M; c++) {
				if(map[r][c] != 0) continue;
				
				map[r][c] = 1;
				recursive(cnt+1, r, c+1);
				map[r][c] = 0;
			}
			cc=0;
		}
		
	}
	
	private static void bfs() {
		int curEmptyCnt = emptyCnt-3; // 새로운 3개의 기둥 개수 빼주기
		
		int[][] copyMap = map.clone();
		for(int i = 0; i < N; i++) {
			copyMap[i] = map[i].clone();
		}
				
		Queue<Point> q = new LinkedList<Point>();
		
		for(int i=0; i<N; i++) {
			for(int j=0; j<M; j++) {
				if(copyMap[i][j] == 2) {
					q.add(new Point(i, j));
				}
			}
		}
		
		Point curPt;
		
		while(!q.isEmpty()) {
			curPt = q.poll();
			
			int nr,nc;
			for(int d=0; d<4; d++) {
				nr = curPt.r+dr[d];
				nc = curPt.c+dc[d];
				
				if(!(nr>=0&&nr<N&&nc>=0&&nc<M)||copyMap[nr][nc] != 0) continue;
				
				copyMap[nr][nc] = 2;
				q.add(new Point(nr,nc));
				
				curEmptyCnt--; //바이러스가 확장된 빈 공간 개수 빼기
			}
		}
		
		if(curEmptyCnt > max) max = curEmptyCnt;
	}
	
	private static void printMap(int[][] curMap) {
		for(int i=0; i<N; i++) {
			for(int j=0; j<M; j++) {
				System.out.print(curMap[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
}