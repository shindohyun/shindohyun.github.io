import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static int T, A, B;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		T = Integer.parseInt(br.readLine());
		StringTokenizer st = null;
		
		for(int t = 0; t < T; t++) {
			st = new StringTokenizer(br.readLine());
			A = Integer.parseInt(st.nextToken());
			B = Integer.parseInt(st.nextToken());	
			solution();
		}
		br.close();
	}
	
	private static void solution() {
		System.out.println(LCM(GCD(A, B), A, B));
	}

	private static int GCD(int n1, int n2) {
		while(n2 != 0) {
			//n1, n2 크기 순서 상관 없음, n1이 n2 보다 작으면 n1은 그대로 나머지가 되고 다음 회차에서 순서가 뒤바뀐다.
			int temp = n1%n2;
			n1 = n2;
			n2 = temp;
		}
		return n1;
	}
	
	private static int LCM(int gcd, int n1, int n2) {
		//GCD*LCM == n1*n2;
		return n1*n2/gcd;
	}
	
}
