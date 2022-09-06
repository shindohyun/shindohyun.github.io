import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static int N;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		StringTokenizer st = new StringTokenizer(br.readLine());
		int cnt = 0;
		while(st.hasMoreTokens()) {
			int num = Integer.parseInt(st.nextToken());
			if(isPrimeNumber(num)) cnt++;
		}
		br.close();
		
		System.out.println(cnt);
	}
	
	//소수: 약수가 1과 자기 자신만 존재한다. 1은 소수가 아니다.
	private static boolean isPrimeNumber(int num) {
		if(num == 1) return false;
		
		for(int i = 2; i < num; i++) {
			if(num%i == 0) return false;
		}
		
		return true;
	}
}
