import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static int N; //2~11
	static int[] digits = new int[11];
	static int[] opCnts = new int[4]; //index 0:+, 1:-, 2:*, 3:/
	static long max = Long.MIN_VALUE;
	static long min = Long.MAX_VALUE;
	
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		StringTokenizer st =  new StringTokenizer(br.readLine());
		for(int i = 0; i < N; i++) {
			digits[i] = Integer.parseInt(st.nextToken());
		}
		st = new StringTokenizer(br.readLine());
		for(int i = 0; i < 4; i++) {
			opCnts[i] = Integer.parseInt(st.nextToken());
		}
		br.close();
		
		solution();
		System.out.println(max);
		System.out.println(min);
	}
	
	private static void solution() {
		recursive(0, digits[0]);
	}
	
	private static void recursive(int cnt, int res) {
		if(cnt >= N-1) {
			if(res > max) max = res;
			if(res < min) min = res;
			return;
		}
		
		for(int i = 0; i < 4; i++) {
			if(opCnts[i] <= 0) continue;
			
			opCnts[i]--;
			recursive(cnt+1, calculate(res, digits[cnt+1], i));
			opCnts[i]++;
		}
	}
	
	private static int calculate(int num1, int num2, int op) {
		switch(op) {
		case 0:
			return num1+num2;
		case 1:
			return num1-num2;
		case 2:
			return num1*num2;
		case 3:
			return num1/num2;
		default:
				return 0;
		}
	}
}
