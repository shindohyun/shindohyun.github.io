import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static int num1, num2;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		num1 = Integer.parseInt(st.nextToken());
		num2 = Integer.parseInt(st.nextToken());
		br.close();
		
		solution();
	}
	
	private static void solution() {
		int gcd = GCD(num1, num2);
		int lcm = LCM(gcd, num1, num2);
		System.out.println(gcd);
		System.out.println(lcm);
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
