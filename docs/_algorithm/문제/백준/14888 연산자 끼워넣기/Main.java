import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	//최댓값, 최소값 출력
	//결과값 범위 -10억~10억
	static int N; //2~11
	static int[] As = new int[11]; //각 1~100
	static int[] Ops = new int[4]; //연산자 개수 0:+, 1:-, 2:*, 3:/
	static long max = Long.MIN_VALUE;
	static long min = Long.MAX_VALUE;
	
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		StringTokenizer st = new StringTokenizer(br.readLine());
		for(int i=0;i<N;i++) {
			As[i] = Integer.parseInt(st.nextToken());
		}
		st = new StringTokenizer(br.readLine());
		for(int i=0;i<4;i++) {
			Ops[i] = Integer.parseInt(st.nextToken());
		}
		br.close();
		
		solution();
		System.out.println(max);
		System.out.println(min);
	}
	
	private static void solution() {
		permutation(0, As[0], 1);
	}
	
	private static void permutation(int cnt, long res, int idx) {
		if(cnt == N-1) {
			if(res > max) max = res;
			if(res < min) min = res;
			return;
		}
		if(cnt > N-1 || idx >= N) return;
		
		for(int i=0;i<4;i++) {
			if(Ops[i] != 0) {
				Ops[i]--;
				permutation(cnt+1, calcultae(res, As[idx], i), idx+1);
				Ops[i]++;
			}
		}
	}
	
	private static long calcultae(long res, int num, int op) {
		switch(op) {
		case 0:
			return res+num;
		case 1:
			return res-num;
		case 2:
			return res*num;
		case 3:
			return res/num;
		default:
			return 0;
		}
	}
}
