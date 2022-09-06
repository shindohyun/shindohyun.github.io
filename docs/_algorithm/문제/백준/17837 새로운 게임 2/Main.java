import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class Main {
	static int N; // 4~12
	static int K; // 4~10
	static LinkedList<Unit>[][] map = new LinkedList[12][12];
	static Unit[] units = new Unit[10];
	static int[][] colorMap = new int[12][12];
	static final int[] dr = {0,0,-1,1};
	static final int[] dc = {1,-1,0,0};
	static int res = 0;
	
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		for(int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j < N; j++) {
				map[i][j] = new LinkedList<Unit>();
				colorMap[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		for(int i = 0; i < K; i++) {
			st = new StringTokenizer(br.readLine());
			int r = Integer.parseInt(st.nextToken())-1;
			int c = Integer.parseInt(st.nextToken())-1;
			int d = Integer.parseInt(st.nextToken())-1;
			Unit u = new Unit(r,c,d);
			units[i] = u;
			map[r][c].addLast(u);
		}
		br.close();
		
		solution();
		System.out.println(res >= 1000 ? -1 : res+1);
	}
	
	public static void solution() {
		while(res < 1000) {
			for(int i = 0; i < K; i++) {
				Unit unit = units[i];
				
				int r = unit.r;
				int c = unit.c;
				int d = unit.d;
				
				int nr = r + dr[d];
				int nc = c + dc[d];
				int nColor; // next color
				
				if(!(nr >= 0 && nr < N && nc >= 0 && nc < N)) nColor = 2; // 벽인 경우 파란색으로 처리
				else nColor = colorMap[nr][nc];
				
				if(nColor == 2) {
					// 파란색인 경우 방향을 바꿔서 다음을 본다.
					switch(unit.d) {
					case 0: unit.d = 1; break;
					case 1: unit.d = 0; break;
					case 2: unit.d = 3; break;
					case 3: unit.d = 2; break;
					}
					
					d = unit.d;
					
					nr = r + dr[d];
					nc = c + dc[d];
					
					// 다음이 파란색이거나 벽인 경우 움직이지 않는다.
					if(!(nr >= 0 && nr < N && nc >= 0 && nc < N) || colorMap[nr][nc] == 2) continue;
					// 다음이 흰색이나 빨간색인 경우 이동을 진행한다.
					else nColor = colorMap[nr][nc];
				}
				
				if(nColor == 0 || nColor == 1) {
					int index = map[r][c].indexOf(unit);
					LinkedList<Unit> temp = new LinkedList<Main.Unit>();
					
					for(int j = map[r][c].size()-1; j >= index; j--) {
						Unit u = map[r][c].get(j);
						map[r][c].remove(u);
						
						u.r = nr;
						u.c = nc;
						temp.addFirst(u);
					}
					
					if(nColor == 1) Collections.reverse(temp);
					
					map[nr][nc].addAll(temp);
					
					if(map[nr][nc].size() >= 4) return;
				}
			}
			
			res++;
		}
	}
	
	static class Unit implements Comparable{
		public int r, c, d;
		public Unit(int r, int c, int d) {
			this.r = r;
			this.c = c;
			this.d = d;
		}
		
		@Override
		public int compareTo(Object o) {
			// TODO Auto-generated method stub
			return 0;
		}
	}
}
