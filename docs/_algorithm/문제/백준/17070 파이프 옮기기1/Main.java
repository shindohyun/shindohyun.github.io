import java.util.Scanner;

public class Main {

	static int N; //3~16
	static int map[][];
	static int tail_r, tail_c, head_r, head_c;
	static int state = 0; //0: 가로, 1: 대각선, 2: 세로
	//0: 오른쪽, 1: 오른쪽 아래, 2: 아래
	static final int dr[] = {0, 1, 1};
	static final int dc[] = {1, 1, 0};
	static int cnt = 0;
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt() + 2;
		map = new int[N][N];
		for(int i = 0; i < N; ++i) {
			for(int j = 0; j < N; ++j) {
				if(i == 0 || i == N-1 || j == 0 || j == N-1)
					map[i][j] = 1;
				else 
					map[i][j] = sc.nextInt();
			}
		}
		tail_r=1; tail_c=1;
		head_r=1; head_c=2;
		sc.close();
		
		//printMap();
		solution();
		System.out.println(cnt);
	}
	
	private static void solution() {
		rec();
	}
	
	private static void rec() {
		if(head_r == N-2 && head_c == N-2) {
			cnt++;
			//System.out.println(tail_r + "," + tail_c + "   " + head_r + "," + head_c + "     " + state + "    " + cnt);
			return;
		}
		
		int ctr = tail_r;
		int ctc = tail_c;
		int chr = head_r;
		int chc = head_c;
		int cstate = state;
		
		//System.out.println(tail_r + "," + tail_c + "   " + head_r + "," + head_c + "     " + state);
		
		for(int d = 0; d < 3; ++d) {
			if(state == 0 && d == 2) continue;
			if(state == 2 && d == 0) continue;
			
			head_r = chr + dr[d];
			head_c = chc + dc[d];
			tail_r = chr;
			tail_c = chc;
			
			if(d == 1) {
				if(map[head_r][head_c] == 0 &&
					map[chr + dr[0]][chc + dc[0]] == 0 &&
					map[chr + dr[2]][chc + dc[2]] == 0 ) {
					state = d;
					rec();
					
					tail_r = ctr;
					tail_c = ctc;
					head_r = chr;
					head_c = chc;
					state = cstate;
				}
			}
			else {
				if(map[head_r][head_c] == 0) {
					state = d;
					rec();
					
					tail_r = ctr;
					tail_c = ctc;
					head_r = chr;
					head_c = chc;
					state = cstate;
				}
			}
		}
	}
	
	private static void printMap() {
		for(int i = 0; i < N; ++i) {
			for(int j = 0; j < N; ++j) {
				System.out.print(map[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
}
