import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static int N,M; //3~50
	static int[][] map = new int[50][50];
	static int cr, cc, cd; //0:북, 1:동, 2:남, 3:서
	static int[] dr = {-1, 0, 1, 0};
	static int[] dc = {0, 1, 0, -1};
	static int cnt = 0;
	
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		st = new StringTokenizer(br.readLine());
		cr = Integer.parseInt(st.nextToken());
		cc = Integer.parseInt(st.nextToken());
		cd = Integer.parseInt(st.nextToken());
		for(int i=0;i<N;i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0;j<M;j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		br.close();
		
		solution();
		System.out.println(cnt);
	}
	
	private static void solution() {
		int nr,nc;
		boolean find = true;
		
		while(true) {
			if(find) {
				//현재 위치 청소
				map[cr][cc] = 2;
				cnt++;
				find = false;
			}
			
			//현재 방향 기준 왼쪽 방향으로 탐색
			//북->서->남->동->북 = 0->3->2->1->0
			int searchCnt = 4;
			
			while(searchCnt-- > 0) {
				cd = cd-1 < 0 ? 3 : cd-1; //회전
				nr = cr+dr[cd];
				nc = cc+dc[cd];
				
				//청소하지 않은 공간 존재
				if(map[nr][nc] == 0) {
					//전진
					cr=nr;
					cc=nc;
					find = true;
					break;
				}
			}
			
			//네방향 모두 청소가 이미 되어있거나 벽인 경우
			if(!find) {
				//후진할 수 있는지 확인
				nr = cr-dr[cd];
				nc = cc-dc[cd];
			
				//후진 가능한 경우 후진
				if(map[nr][nc] != 1) {
					cr = nr;
					cc = nc;
				}
				//후진할 수 없는 경우 종료
				else {
					break;
				}
			}
		}
	}
}
