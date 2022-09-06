import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
	static int T;
	static int m[] = new int[11];
	
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		T = Integer.parseInt(br.readLine());
		for(int t=0; t<T; t++) {
			solution(Integer.parseInt(br.readLine()));
		}
		br.close();
	}
	
	private static void solution(int num) {
		for(int i = 0; i < m.length; i++) {
			m[i] = -1;
		}
		System.out.println(dp(num));
	}
	
	//해당 수를 1,2,3으로 계속 빼준다. 0을 만들 수 있다면 1,2,3의 합으로 표현 가능한 수 이다.
	private static int dp(int num) {
		if(num == 0) return 1;
		if(num < 0) return 0;
		if(m[num] >= 0) return m[num];
		
		return m[num] = dp(num-1) + dp(num-2) + dp(num-3);
	}
}
