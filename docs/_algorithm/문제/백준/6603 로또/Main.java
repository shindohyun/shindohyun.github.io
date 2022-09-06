import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static int k; //7~12
	static int[] S = new int[12];
	static boolean[] visit = new boolean[12];
	static StringBuilder sb = new StringBuilder();
	
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		do {
			st = new StringTokenizer(br.readLine());
			k = Integer.parseInt(st.nextToken());
			if(k == 0) break;
			for(int i=0; i<k; i++) {
				S[i] = Integer.parseInt(st.nextToken());
			}
			solution();
			sb.append('\n');
		}while(true);
		br.close();
		
		System.out.println(sb);
	}

	private static void solution() {
		combination(0, 0);
	}
	
	private static void combination(int cnt, int idx) {
		if(cnt == 6) {
			for(int i=0; i<k; i++) {
				if(visit[i]) {
					sb.append(S[i] + " ");
				}
			}
			sb.append('\n');
			return;
		}
		
		if(cnt > 6 || idx >= k || visit[idx]) return;
		
		visit[idx] = true;
		combination(cnt+1, idx+1);
		visit[idx] = false;
		
		combination(cnt, idx+1);
	}
}