import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static int N; //1~20
	static long S; //-1,000,000 ~ 1,000,000
	static int[] nums = new int[20];
	static boolean[] visit = new boolean[20];
	static int resCnt = 0;
	
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		S = Long.parseLong(st.nextToken());
		st = new StringTokenizer(br.readLine());
		for(int i = 0; i < N; i++) {
			nums[i] = Integer.parseInt(st.nextToken());
		}
		br.close();
		
		solution();
		System.out.println(resCnt);
	}
	
	private static void solution() {
		for(int i = 1; i <= N; i++) {
			combination(i, 0, 0, 0);
		}
	}
	
	private static void combination(int cnt, int curCnt,int idx, long sum) {
		if(curCnt == cnt) {
			if(sum == S) resCnt++;
			return;
		}
		
		if(curCnt > cnt || idx >= N || visit[idx]) return;
		
		visit[idx] = true;
		combination(cnt, curCnt+1, idx+1, sum+nums[idx]);
		visit[idx] = false;
		
		combination(cnt, curCnt, idx+1, sum);
	}
}
