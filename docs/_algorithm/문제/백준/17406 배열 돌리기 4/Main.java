import java.util.Scanner;

public class Main {
	static int N, M, K;
	static int map[][];
	static int ops_r[];
	static int ops_c[];
	static int ops_s[];
	static int min = Integer.MAX_VALUE;
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt();
		M = sc.nextInt();
		K = sc.nextInt();
		map = new int[N][M];
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < M; j++) {
				map[i][j] = sc.nextInt();
			}
		}
		ops_r = new int[K];
		ops_c = new int[K];
		ops_s = new int[K];
		for(int i = 0; i < K; i++) {
			ops_r[i] = sc.nextInt()-1;
			ops_c[i] = sc.nextInt()-1;
			ops_s[i] = sc.nextInt();
		}
		sc.close();
		
		solution();
		System.out.println(min);
	}
	
	private static void solution() {
		dp(0, new int[K], new boolean[K]);
	}
	
	//연산 순서 정하기
	private static void dp(int cnt, int[] order, boolean[] visit) {
		if(cnt == K) {
//			for(int i = 0; i < cnt; i++) {
//				System.out.print(order[i] + " ");
//			}
//			System.out.println();
			
			//연산 순서에 따라 연산
			calculate(order);
			
			return;
		}
		
		
		for(int i = 0; i < K; i++) {
			if(visit[i] == true) continue;
			
			visit[i] = true;
			order[cnt] = i;
			dp(cnt + 1, order, visit);
			order[cnt] = 0;
			visit[i] = false;
		}
	}
	
	private static void calculate(int order[]) {
		int calMap[][] = map.clone();
		for(int i = 0; i < N; i++) {
			calMap[i] = map[i].clone();
		}
		
		//회전연산 수행
		for(int i = 0; i < K; i++) {
			int op_r = ops_r[order[i]];
			int op_c = ops_c[order[i]];
			int op_s = ops_s[order[i]];
			
			int start_r = op_r - op_s;
			int start_c = op_c - op_s;
			int end_r = op_r + op_s;
			int end_c = op_c + op_s;
			
			if(!(start_r >= 0 && end_r < N && start_c >= 0 && end_c < M)) continue;
			
			int newMap[][] = calMap.clone();
			for(int j = 0; j < N; j++) {
				newMap[j] = calMap[j].clone();
			}
			
			while(true) {
				for(int r = start_r; r <= end_r; r++ ) {
					for(int c = start_c; c <= end_c; c++) {
						if(r == start_r) {
							if(c+1 <= end_c)
								newMap[r][c+1] = calMap[r][c];
							else
								newMap[r+1][c] = calMap[r][c];
						}
						else if(c == end_c) {
							if(r+1 <= end_r)
								newMap[r+1][c] = calMap[r][c];
							else
								newMap[r][c-1] = calMap[r][c];
						}
						else if(r == end_r) {
							if(c-1 >= start_c)
								newMap[r][c-1] = calMap[r][c];
							else
								newMap[r-1][c] = calMap[r][c];
						}
						else if(c == start_c) {
							if(r-1 >= start_r)
								newMap[r-1][c] = calMap[r][c];
							else
								newMap[r][c+1] = calMap[r][c];
						}					
					}
				}
				
				//printMap(calMap);
				
				start_r++;
				end_r--;
				start_c++;
				end_c--;
				
				if(start_r == end_r || start_c == end_c)
					break;
			}
			
			calMap = newMap;
		}
		
		//계산
		for(int r=0;r<N;r++) {
			int sum = 0;
			for(int c=0;c<M;c++) {
				sum+=calMap[r][c];
			}
			if(min > sum) {
				min = sum;
			}
		}
	}
	
	private static void printMap(int[][] printMap) {
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < M; j++) {
				System.out.print(printMap[i][j] + " "); 
			}
			System.out.println();
		}
		System.out.println();
	}
}
