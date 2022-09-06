import java.util.Scanner;

public class Main_20191229 {

	static char[] input;
	static boolean[] visit;
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		input = sc.next().toCharArray();
		visit = new boolean[input.length];
		sc.close();
		
		dp(0, "");
	}
	
	public static void dp(int cnt, String c) {
		if(cnt == input.length) {
			System.out.print(c + " ");
			return;
		}
		
		for(int i = 0; i < input.length; i++) {
			if(visit[i] == true) continue;
			
			visit[i] = true;
			dp(cnt + 1, c + input[i]);
			visit[i] = false;
		}
	}
}
