import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Main {
	static int N, M; //1~8
	static int[] nums = new int[8];
	static StringBuilder sb;
	
	public static void main(String[] args) throws IOException{
		sb = new StringBuilder();
		
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		br.close();
		
		solution();
		
		bw.write(sb.toString());
		bw.flush();
		bw.close();
	}
	
	private static void solution() throws IOException {
		permutation(0, 0);
	}
	
	private static void permutation(int cnt, int flag) throws IOException {
		if(cnt == M) {
			for(int i = 0; i < M; i++) {
				sb.append(nums[i] + " ");
			}
			sb.append('\n');
			return;
		}
		
		if(cnt > M) return;
		
		for(int i = 0; i < N; i++) {
			if((flag & 1<<i) != 0) continue;
			
			nums[cnt] = i+1;
			permutation(cnt+1, flag | 1<<i);
			nums[cnt] = 0;
		}
	}
}
