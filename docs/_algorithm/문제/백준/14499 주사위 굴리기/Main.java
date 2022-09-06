import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	//맵 0 -> 주사위 바닥면 수를 맵에 복사
	//맵 0X -> 맵 수를 주사위 바닥면에 복사 -> 맵 0
	//이동할때마다 상단에 쓰여있는 값은?
	//맵 바깥으로 이동하면 무시, 출력 X
	//맵 0~10, 주사위 시작 위치 0
	static int N; //세로 1~20
	static int M; //가로 1~20
	static int cr=0; //주사위 현재 위치 0~N-1
	static int cc=0; //주시위 현재 위치 0~M-1
	static int K; //명령 개수 1~1,000
	static int[][] map = new int[20][20];
	static int d; //이동방향 동:1, 서:2, 북:3, 남:4
	static int[] dr = {0,0,-1,1};
	static int[] dc = {1,-1,0,0};
	static StringBuilder sb;
	/*
	 *   1
	 * 3 0 2
	 *   4
	 *   5
	 */
	static int[] status = new int[6];
	
	public static void main(String[] args) throws IOException{
		sb = new StringBuilder();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		cr = Integer.parseInt(st.nextToken());
		cc = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		for(int i=0;i<N;i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0;j<M;j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		st = new StringTokenizer(br.readLine());
		for(int i=0;i<K;i++) {
			d = Integer.parseInt(st.nextToken())-1;
			solution();
		}
		br.close();
		
		System.out.println(sb);
	}
	
	private static void solution() {
		int nr, nc;
		nr = cr+dr[d];
		nc = cc+dc[d];
		
		//주사위가 이동 가능한지 확인
		if(!(nr>=0 && nr<N && nc>=0 && nc<M)) return;
		
		//이동 가능한 경우 이동
		cr = nr;
		cc = nc;
		
		//주사위 회전
		rotation();
		
		int cn = map[cr][cc];
		
		//이동 위치의 지도 번호가 0인 경우, 주사위 바닥면 번호 복사
		if(cn == 0) {
			map[cr][cc] = status[5];
		}
		//이동 위치의 지도 번호가 0이 아닌 경우, 현재 지도 번호를 주사위 바닥면에 복사하고 지도 번호 0
		else {
			status[5] = cn;
			map[cr][cc] = 0;
		}
		
		//현재 주사위 윗면 번호
		if(sb.length() > 0) sb.append("\n"+status[0]);
		else sb.append(status[0]);
	}
	
	private static void rotation() {
		int temp;
		switch(d) {
		case 0:
			//동 : 0->2, 2->5, 5->3, 3->0
			temp = status[0];
			status[0] = status[3];
			status[3] = status[5];
			status[5] = status[2];
			status[2] = temp;	
			break;
		case 1:
			//서 : 0->3, 3->5, 5->2, 2->0
			temp = status[0];
			status[0] = status[2];
			status[2] = status[5];
			status[5] = status[3];
			status[3] = temp;
			break;
		case 2:
			//북 : 0->1, 1->5, 5->4, 4->0
			temp = status[0];
			status[0] = status[4];
			status[4] = status[5];
			status[5] = status[1];
			status[1] = temp;
			break;
		case 3:
			//남 : 0->4, 4->5, 5->1, 1->0
			temp = status[0];
			status[0] = status[1];
			status[1] = status[5];
			status[5] = status[4];
			status[4] = temp;
			break;
		}
	}
}