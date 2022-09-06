import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	
	static int N; //1~50
	static int L, R; //1~100
	static int[][] map = new int[50][50];
	static int[][] union = new int[50][50];
	static int cnt = 0;
	static int[] dr = {-1,1,0,0};
	static int[] dc = {0,0,-1,1};
	static UnionInfo[] unionInfos = new UnionInfo[2500]; //최소 두 개 국가가 연합을 만들기 때문에
	
	//국경선 열기(연합) 조건: 국경선 공유, 인구 차이 L<= <=R
	//조건에 맞는게 하나도 없으면 종료
	//국경선 모두 열고 인구 이동
	//연합인구수/연합국 칸수로 연합 인구 변경
	//연합 해체
	
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		L = Integer.parseInt(st.nextToken());
		R = Integer.parseInt(st.nextToken());
		for(int i=0; i<N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0; j<N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
				unionInfos[N*i+j] = new UnionInfo();
			}
		}
		br.close();
		
		solution();
		System.out.println(cnt);
	}
	
	private static void solution() {
		while(true) {
			int unionCnt = 0;
			
			//연합국 검사 맵 초기화
			for(int r=0; r<N; r++) {
				for(int c=0; c<N; c++) {					
					union[r][c] = 0;
				}
			}
			
			//연합국 검사
			for(int r=0; r<N; r++) {
				for(int c=0; c<N; c++) {
					if(union[r][c] != 0) continue;
					if(bfs(r,c,unionCnt)) unionCnt++;
				}
			}
			
			
			//연합국이 발견되지 않으면 종료
			if(unionCnt == 0) {
				break;
			}
			
			//각 연합국의 인구 이동
			for(int i = 0; i<unionCnt; i++) {
				int unionNum = i+1;
				
				int p = unionInfos[i].total/unionInfos[i].cnt;
				
				for(int r=0; r<N; r++) {
					for(int c=0; c<N; c++) {					
						if(union[r][c] == unionNum) {
							map[r][c] = p;
						}
					}
				}
			}
			cnt++;
			//printMap();
		}
	}
	
	private static boolean bfs(int r, int c, int num) {
		int unionNum = num+1;
		
		Queue<Point> q = new LinkedList<Main.Point>();
		q.add(new Point(r,c));
		
		Point curPt;
		int nr, nc;
		boolean found = false;
		while(!q.isEmpty()) {
			curPt = q.poll();
			
			for(int d=0; d<4; d++) {
				nr = curPt.r+dr[d];
				nc = curPt.c+dc[d];
				
				if(!(nr>=0&&nr<N&&nc>=0&&nc<N) || union[nr][nc] != 0) continue;
				
				int diff = Math.abs(map[curPt.r][curPt.c]-map[nr][nc]);
				if(!(diff>=L&&diff<=R)) continue;
				
				//처음 찾는 경우
				if(!found) {
					found = true;
					union[curPt.r][curPt.c] = unionNum;
					unionInfos[unionNum-1].total = map[curPt.r][curPt.c];
					unionInfos[unionNum-1].cnt = 1;
				}
				
				union[nr][nc] = unionNum;
				unionInfos[unionNum-1].total += map[nr][nc];
				unionInfos[unionNum-1].cnt++;
				q.add(new Point(nr, nc));
			}
		}
		
		return found;
	}
	
	private static void printMap() {
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				System.out.print(map[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	static class Point{
		public int r, c;
		public Point() {}
		public Point(int r, int c) {
			this.r=r;
			this.c=c;
		}
	}
	
	static class UnionInfo{
		public int total, cnt;
		public UnionInfo() {
			
		}
	}
}
