import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	static class Point {
		public int r, c;
		public Point() {}
		public Point(int r, int c) {
			this.r=r;
			this.c=c;
		}
	}
	
	static int M, N, K; //1~100 M=column, N=row
	static int[][] map = new int[100][100];
	static int cnt = 0;
	static int[] dr= {-1,1,0,0};
	static int[] dc= {0,0,-1,1};
	static ArrayList<Integer> res = new ArrayList<Integer>();
	
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		M = Integer.parseInt(st.nextToken());
		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		Point leftTop = new  Point();
		Point rightBottom = new  Point();
		for(int i=0; i<K; i++) {
			st = new StringTokenizer(br.readLine());
			leftTop.r = Integer.parseInt(st.nextToken());
			leftTop.c = Integer.parseInt(st.nextToken());
			rightBottom.r = Integer.parseInt(st.nextToken())-1;
			rightBottom.c = Integer.parseInt(st.nextToken())-1;
			
			mark(leftTop, rightBottom);
		}
		br.close();
		
		solution();
		System.out.println(res.size());
		res.sort(null);
		for(int i=0; i<res.size(); i++) {
			System.out.print(res.get(i) + " ");
		}
	}
	
	//맵에 사각형 표시
	private static void mark(Point lt, Point rb) {
		for(int r=lt.r; r<=rb.r; r++) {
			for(int c=lt.c; c<=rb.c; c++) {
				map[r][c] = -1;
			}
		}
	}
	
	private static void solution() {
		for(int i=0; i<N; i++) {
			for(int j=0; j<M; j++) {
				if(map[i][j] != 0) continue;
				bfs(new Point(i,j), ++cnt);
			}
		}
	}
	
	private static void bfs(Point seed, int number) {
		int area = 0;
		
		Queue<Point> q = new LinkedList<Point>();
		q.add(seed);
		map[seed.r][seed.c] = number;
		area++;
		
		Point curPt;
		int nr,nc;
		while(!q.isEmpty()) {
			curPt = q.poll();
			
			for(int d=0; d<4; d++) {
				nr = curPt.r+dr[d];
				nc = curPt.c+dc[d];
				
				if(!(nr>=0&&nr<N&&nc>=0&&nc<M) || map[nr][nc] != 0) continue;
				
				map[nr][nc] = number;
				q.add(new Point(nr,nc));
				area++;
			}
		}
		
		res.add(area);
	}
	
	private static void printMap() {
		for(int i=0; i<N; i++) {
			for(int j=0; j<M; j++) {
				int input = map[i][j];
				System.out.print(input==-1 ? "* ": input+" ");
			}
			System.out.println();
		}
		System.out.println();
	}
}