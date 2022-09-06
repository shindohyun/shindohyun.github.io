import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	static class Point{
		public int r,c;
		public Point() {}
		public Point(int r, int c) {
			this.r=r;
			this.c=c;
		}
	}
	static int N, M; //2~100
	static int[][] map = new int[100][100];
	static int min = Integer.MAX_VALUE;
	static int[] dr= {-1,1,0,0};
	static int[] dc= {0,0,-1,1};
	
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		String row;
		for(int i=0; i<N; i++) {
			row = br.readLine();
			for(int j=0; j<row.length(); j++) {
				map[i][j] = row.charAt(j) - '0';
			}
		}
		br.close();
		
		solution();
		System.out.println(min);
	}
	
	private static void solution() {
		bfs();
	}
	
	private static void bfs() {
		Queue<Point> q = new LinkedList<Main.Point>();
		boolean[][] visit = new boolean[N][M];
		
		q.add(new Point(0,0));
		visit[0][0] = true;
		
		Point curPt;
		int nr, nc;
		int depth = 0;
		int length;
		while(!q.isEmpty()) {
			length = q.size();
			depth++;
			
			for(int i=0; i<length; i++) {
				curPt = q.poll();
				if(curPt.r==N-1 && curPt.c==M-1) {
					min = depth;
					return;
				}
				
				for(int d=0; d<4; d++) {
					nr= curPt.r+dr[d];
					nc= curPt.c+dc[d];
					
					if(!(nr>=0&&nr<N&&nc>=0&&nc<M)|| visit[nr][nc] || map[nr][nc] == 0) continue;
					
					visit[nr][nc] = true;
					q.add(new Point(nr, nc));
				}				
			}
		}
	}
	
	private static void printMap() {
		for(int i=0; i<N; i++) {
			for(int j=0; j<M; j++) {
				System.out.print(map[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
}