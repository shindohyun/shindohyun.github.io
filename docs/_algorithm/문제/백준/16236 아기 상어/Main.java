import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	static class Point{
		public int r, c;
		public Point(int r, int c) {
			this.r = r;
			this.c = c;
		}
	}
	
	static int N; //2~20
	static int[][] map = new int[20][20];
	static Point curPt = null;
	static int w = 2;
	static int eat = 0;
	static int cnt = 0;
	static int[] dr = {-1, 1, 0, 0};
	static int[] dc = {0, 0, -1, 1};
	
	public static void main(String[] args) throws IOException{	
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		StringTokenizer st = null;
		for(int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j < N; j++) {
				int input = Integer.parseInt(st.nextToken());
				if(input==9) {
					curPt = new Point(i, j);
				}
				else {
					map[i][j] = input;
				}
			}
		}
		br.close();
		
		solution();
		System.out.println(cnt);
	}
	
	public static void solution() {
		while(true) {
			int moveCnt = bfs();
			if(moveCnt == 0) break;
			else {
				cnt += moveCnt;
			}
		}
		
	}
	
	public static int bfs() {		
		Queue<Point> q = new LinkedList<Point>();
		boolean[][] visit = new boolean[N][N];
		q.add(curPt);
		visit[curPt.r][curPt.c] = true;
		
		int depth = 0;
		
		ArrayList<Point> eatList = new ArrayList<Point>(); //같은 깊이에서 먹을 수 있는 모든 물고기 위치 저장
		
		while(!q.isEmpty()) {
			int length = q.size();
			depth++;
			eatList.clear();
			
			for(int i = 0; i < length; i++) {
				Point pt = q.poll();
				int nr, nc;
				
				for(int d = 0; d < 4; d++) {
					nr = pt.r + dr[d];
					nc = pt.c + dc[d];
					
					if(!(nr >= 0 && nr < N && nc >= 0 && nc < N) || visit[nr][nc]) continue;
					
					int fishWeight =  map[nr][nc];
					
					if(fishWeight > w) continue;
					else {
						visit[nr][nc] = true;
						q.add(new Point(nr,nc));
						
						//먹기
						if(fishWeight != 0 && fishWeight < w) {
							eatList.add(new Point(nr, nc));
						}
					}
				}
			}
			
			//가장 위에 있고 가장 왼쪽에 있는
			Point minPt = new Point(N, N);
			for(Point eatItem : eatList) {
				if(minPt.r > eatItem.r) {
					minPt = eatItem;
				}
				else if(minPt.r == eatItem.r){
					if(minPt.c > minPt.c) {
						minPt = eatItem;
					}
				}
			}
			
			if(minPt.r != N && minPt.c != N) {
				eat++;
				if(eat == w) {
					w++;
					eat = 0;
				}
				map[minPt.r][minPt.c] = 0;
				
				//이동
				curPt.r = minPt.r;
				curPt.c = minPt.c;
				return depth;
			}
		}
		
		return 0; //먹을 물고기가 없는 경우
 	}
	
	static public void printMap() {
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < N; j++) {
				System.out.print(map[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println("size:" + w  + " cnt:" + cnt + " eat:"+eat);
	}
}
