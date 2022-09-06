import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

	/*
	 * 목적: 종료 시 횟수 출력 (첫 번째 시도가 1회)
	 * 
	 * 조건:
	 * 1. 컨베이어벨트
	 * - N (2~100), 실제 길이 2N
	 * - 올라가는 위치 1, 내려가는 위치 N: 인덱스로 보면 각각 0, N-1
	 * - 각 위치 내구성 Ai (1~1,000)
	 * - 로봇이 (위치 1에서) 올라가거나 이동할 경우 그 위치 내구도 -1
	 * - 내구도 0인 곳에는 올라가거나 이동 불가
	 * 2. 이동
	 *  1) 벨트 회전
	 *  2) 로봇 이동
	 *   - 가장 먼저 벨트에 올라간 로봇 부터 (N위치 부터 1로 검사하면 된다.)
	 *   - 벨트 회전 방향으로 이동할 수 있으면 이동
	 *   - 이동 조건: 이동하려는 위치에 로봇 없고 내구도 1이상
	 *  3) 로봇 올릴 수 있으면 올리기
	 *   - 올리기 조건: 이동 조건과 동일
	 * 3. 종료
	 * - 내구도가 0인 위치가 K개 이상인 경우
	 * 
	 * 알고리즘:
	 * 1. while문 반복
	 * 2. 벨트 회전
	 *  - 회전 후 내리는 위치의 로봇 제거
	 * 3. 로봇 이동 (인덱스 N-2 -> 0 으로 탐색, N-1은 내리는 위치이니까 검사하지 않음)
	 *  1) 이동 후 내구도 -1, 0이 된다면 K -1 (내구도 0인 위치 제한 개수 감소)
	 *  2) 이동 후 내리는 위치라면 로봇 제거
	 * 4. 로봇 올리기
	 * 5. 내구도 0인 위치 개수 파악
	 */
	static int N;
	static int len;
	static int K;
	static Point[] conv = new Point[200];
	static int cnt; // 횟수
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		len = N*2;
		K = Integer.parseInt(st.nextToken());
		st = new StringTokenizer(br.readLine());
		for(int i = 0; i < len; i++) {
			conv[i] = new Point(Integer.parseInt(st.nextToken()), false);
		}
		br.close();
		
		solution();
		System.out.println(cnt);
	}
	
	public static void solution() {
		cnt = 1;
		
		while(true) {
			moveConv();
			moveRobot();
			
			// 로봇 올리기
			if(canMove(0)) {
				conv[0].robot = true;
				conv[0].d--;
				if(conv[0].d == 0) K--; // 올린 위치 내구도가 0이면 내구도 0인 위치 제한 개수 감소
			}
			
			// 내구도 0인 위치 개수 파악, 종료 조건 검사
			if(K <= 0) break;
			
			cnt++;
		}
	}
	
	public static void moveRobot() {
		for(int i = N-2; i >= 0; i--) {
			if(conv[i].robot == false) continue;
			
			int next = i+1;
			if(!canMove(next)) continue;
			
			// 이동
			conv[i].robot = false;
			conv[next].robot = true;
			
			conv[next].d--; // 이동한 위치 내구도 -1
			if(conv[next].d == 0) K--; // 이동한 위치 내구도가 0이면 내구도 0인 위치 제한 개수 감소
			if(next == N-1) conv[next].robot = false; // 이동한 위치가 내리는 위치라면 로봇 제거
		}
	}
	
	public static boolean canMove(int pos) {
		if(conv[pos].d >= 1 && conv[pos].robot == false) return true;
		else return false;
	}
	
	public static void moveConv() {
		Point last = conv[len-1];
		
		for(int i = len-2; i >= 0; i--) {
			conv[i+1] = conv[i];
		}
		
		conv[0] = last;
		conv[N-1].robot = false; // 이동 후 내리는 위치의 로봇 제거
	}
	
	public static void printMap() {
		for(int i = 0; i < N; i++) {
			System.out.print(conv[i].d + " ");
		}
		System.out.println();
		for(int i = len-1; i >= N; i--) {
			System.out.print(conv[i].d + " ");
		}
		System.out.println();
		System.out.println();
	}
	
	static class Point{
		public int d; // 내구도
		public boolean robot; // 로봇 존재 유무
		public Point(int d, boolean robot) {
			this.d = d;
			this.robot = robot;
		}
	}
}
