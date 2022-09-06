import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static int N; //4~20
	static int[][] map = new int[20][20];
	static boolean[] team = new boolean[20];
	static int min = Integer.MAX_VALUE;
	
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		StringTokenizer st = null;
		for(int i=0;i<N;i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0;j<N;j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		br.close();
		
		solution();
		System.out.println(min);
	}
	
	private static void solution() {
		//permutation(0, 0);
		combination();
	}
	
	private static void permutation(int cnt, int idx) {
		if(cnt == N/2) {
			//계산
			int sumA = 0;
			int sumB = 0;
			for(int j = 0; j < N; j++) {
				if(team[j]) {
					for(int z = j+1; z < N; z++) {
						if(team[z]) {
							sumA += map[j][z];
							sumA += map[z][j];
						}
					}
				}
				else {
					for(int z = j+1; z < N; z++) {
						if(!team[z]) {
							sumB += map[j][z];
							sumB += map[z][j];
						}
					}
				}
			}
			
			int diff = Math.abs(sumA-sumB);
			if(diff < min) min = diff;
			return;
		}
		
		if(cnt > N/2 || idx >= N) return;
		
		for(int i = idx; i < N; i++) {
			team[i] = true;
			permutation(cnt+1, i+1);
			team[i] = false;
		}
	}
	
	private static void combination() {
		//N개 중 N/2개를 뽑는 조합
		int total = 1<<N;
		
		for(int i = 0; i < total; i++) {
			if(Integer.bitCount(i) != N/2) continue;
			
			team = new boolean[20];
			
			for(int j = 0; j < N; j++) {
				if((i&(1<<j)) != 0) team[j] = true;
			}
			
			//계산
			int sumA = 0;
			int sumB = 0;
			for(int j = 0; j < N; j++) {
				if(team[j]) {
					for(int z = j+1; z < N; z++) {
						if(team[z]) {
							sumA += map[j][z];
							sumA += map[z][j];
						}
					}
				}
				else {
					for(int z = j+1; z < N; z++) {
						if(!team[z]) {
							sumB += map[j][z];
							sumB += map[z][j];
						}
					}
				}
			}
			
			int diff = Math.abs(sumA-sumB);
			if(diff < min) min = diff;
		}
	}
}
