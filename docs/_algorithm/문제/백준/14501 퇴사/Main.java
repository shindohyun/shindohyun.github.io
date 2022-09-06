import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	
	static int N; //1~15
	static int[] Ts = new int[15]; //1~5
	static int[] Ps = new int[15]; //1~1,000
	static long max = Long.MIN_VALUE;
	
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		StringTokenizer st = null;
		for(int i=0;i<N;i++) {
			st = new StringTokenizer(br.readLine());
			Ts[i] = Integer.parseInt(st.nextToken());
			Ps[i] = Integer.parseInt(st.nextToken());
		}
		br.close();
		
		solution();
		System.out.println(max);
	}
	
	private static void solution() {
		recursive(0, 0);
	}
	
	private static void recursive(int day, int sum) {
		if(day >= N) {
			if(sum > max) max = sum;
			return;
		}
		
		//day에 일을 하는 경우
		int workPeriod= Ts[day]; //걸리는 시간
		int endDay = day+workPeriod-1; //끝나는 날짜
		//끝나는 날짜가 퇴사일을 넘어가면 일할 수 없다.
		if(endDay >= N) {
			if(sum > max) max = sum;	
		}
		else {
			recursive(endDay+1, sum+Ps[day]);	
		}

		//day에 일을 하지 않는 경우
		recursive(day+1, sum);
	}
}
