import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;


public class Main {
	
	static class Point{
		public int r, c;
		public Point() {}
		public Point(int r, int c) {
			this.r=r;
			this.c=c;
		}
	}
	static int N; //4~50
	static int M; //1~10
	static int[][] map = new int[50][50];
	static Point[] seed = new Point[10];//처음 활성 바이러스 저장
	static int min = Integer.MAX_VALUE;
	static int emptyCnt = 0;
	static int[] dr = {-1,1,0,0};
	static int[] dc = {0,0,-1,1};
	
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		int input = 0;
		for(int i=0; i<N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0; j<N; j++) {
				input = Integer.parseInt(st.nextToken());
				map[i][j] = input; 
				if(input == 0) emptyCnt++;
			}
		}
		for(int i=0; i<M; i++) {
			seed[i] = new Point();
		}
		br.close();
		
		solution();
		System.out.println(min == Integer.MAX_VALUE ? -1 : min);
	}
	
	private static void solution() {
		if(emptyCnt == 0) {
			min=0;
			return;
		}
		recursive(0, 0, 0);
	}
	
	private static void recursive(int cnt, int cr, int cc) {
		if(cnt == M) {
			bfs();
			return;
		}
		
		if(cnt > M) return;
		
		for(int r=cr; r<N; r++) {
			for(int c=cc; c<N; c++) {
				if(map[r][c] != 2) continue;
				seed[cnt].r = r;
				seed[cnt].c = c;
				recursive(cnt+1, r, c+1);
			}
			cc=0;
		}
	}
	
	private static void bfs() {
		Queue<Point> q = new LinkedList<Main.Point>();
		for(int i =0; i<M; i++) {
			q.add(seed[i]);
		}
		
		int[][] curMap = map.clone();
		for(int i=0; i<N; i++) {
			curMap[i] = map[i].clone();
		}
			
		int curEmptyCnt = emptyCnt;
		
		Point curPt;
		int depth = 0;
		int length = 0;
		
		while(!q.isEmpty()) {
			length = q.size();
			depth++;
			for(int i=0; i<length; i++) {
				curPt = q.poll();
				curMap[curPt.r][curPt.c] = 3; //활성바이러스 표시
				
				int nr, nc;
				for(int d=0; d<4; d++) {
					nr = curPt.r+dr[d];
					nc = curPt.c+dc[d];
					
					if(!(nr>=0&&nr<N&&nc>=0&&nc<N) || curMap[nr][nc] == 3 || curMap[nr][nc] == 1) continue;
					
					if(curMap[nr][nc] == 0) curEmptyCnt--;
					if(curEmptyCnt==0) {
						if(min > depth) min = depth;
						return;
					}
					curMap[nr][nc] = 3;
					q.add(new Point(nr,nc));
				}
			}
			
			//printMap(curMap);
		}
	}
	
	private static void printMap(int[][] curMap) {
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				System.out.print(curMap[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
}
