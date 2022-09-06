import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	
	static int N; //2~10
	static int[][] map = new int[10][10];
	static boolean[] visit = new boolean[10];
	static long min = Long.MAX_VALUE; //행렬 각 성분 0~1000000
	
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		StringTokenizer st = null;
		for(int i = 0; i < N; ++i) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j < N; ++j) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		br.close();
		
		solution();
		System.out.println(min);
	}
	
	private static void solution() {
		for(int i = 0; i < N; i++) {
			visit[i] = true;
			backtracking(i, i, 0, 1);
			visit[i] = false;
		}
	}
	
	private static void backtracking(int start, int cur, int sum, int cnt) {
		if(cnt == N) {
			//다시 처음으로 돌아갈 수 있는지 확인
			if(map[cur][start] != 0) {
				sum += map[cur][start];
				if(min > sum)
					min = sum;
			}
			return;
		}
		
		if(cnt > N || sum >= min) return;
		
		for(int i = 0; i < N; i++) {
			if(map[cur][i] != 0 && !visit[i]) {
				visit[i] = true;
				backtracking(start, i, sum+map[cur][i], cnt+1);
				visit[i] = false;
			}
		}
	}
	
	private static void printMap() {
		for(int i = 0; i < N; ++i) {
			for(int j = 0; j < N; ++j) {
				System.out.print(map[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
}
