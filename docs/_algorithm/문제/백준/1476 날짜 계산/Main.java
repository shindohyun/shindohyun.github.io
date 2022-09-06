import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static int E,S,M; //1 ≤ E ≤ 15, 1 ≤ S ≤ 28, 1 ≤ M ≤ 19
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		E = Integer.parseInt(st.nextToken());
		S = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		br.close();
		
		solution();
	}
	
	private static void solution() {
		int year = 1;
		
		while(true) {
			int e=year; 
			int s=year;
			int m=year;
			
			e %= 15;
			s %= 28;
			m %= 19;
			
			if(e == 0) e = 15;
			if(s == 0) s = 28;
			if(m == 0) m = 19;
			
			if(e == E && s == S && m == M) {
				System.out.println(year);
				break;
			}
			
			year++;
		}
	}
}
