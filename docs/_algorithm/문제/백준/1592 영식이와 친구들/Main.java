import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static int N; // 3~1000
	static int M; // 1~1000
	static int L; // 2~999
	static int[] people = new int[1000];
	static int ball = 0;
	static int res = 0;
	
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		L = Integer.parseInt(st.nextToken());
		people[ball]++;
		
		while(true) {
			int cnt = people[ball];
			
			if(cnt == M) break;
			
			res++;
			
			if(cnt%2 == 0) {
				for(int i = 0; i < L; i++) {
					ball--;
					if(ball < 0) ball = N-1;
				}
			}
			else {
				for(int i = 0; i < L; i++) {
					ball++;
					if(ball >= N) ball = 0;
				}
			}
			
			people[ball]++;
		}
		br.close();
		System.out.println(res);
	}

}
