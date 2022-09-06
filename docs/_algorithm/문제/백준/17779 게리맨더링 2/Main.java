import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	static int N; 
	static int[][] map = new int[20][20];
	static int[][] section = new int[20][20];
	static int[] pr = new int[4];
	static int[] pc = new int[4];
	//시계반대방향 대각선으로 이동
	static int[] ccwr = {1,1,-1,-1};
	static int[] ccwc = {-1,1,1,-1};
	static int[] dr = {-1,1,0,0};
	static int[] dc = {0,0,-1,1};
	static int total = 0; //맵 전체의 총 인구수
	static int[] people = new int[5]; //각 구역의 인구수
	static int res = Integer.MAX_VALUE;
	
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		N = Integer.parseInt(br.readLine());
		for(int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j < N; j++) {
				int w = Integer.parseInt(st.nextToken());
				map[i][j] = w;
				total += w;
			}
		}
		br.close();
		
		solution();
		System.out.println(res);
	}
	
	public static void solution() {
		for(int r = 0; r < N-2; r++) {
			for(int c = 0; c < N-1; c++) {
				for(int d1 = 1; r+d1 < N && c-d1 >= 0; d1++) {
					for(int d2 = 1; r+d2 < N && c+d2 <N; d2++) {
						if(!(r+d1+d2 <= N-1 && c-d1+d2 <= N-2)) {
							continue;
						}
						
						if(makeSection(r, c, d1, d2)) {
							//printSection();
							calculate();
						}
					}
				}
			}
		}
	}
	
	public static void calculate() {
		int sum = 0;
		for(int i = 0; i < 4; i++) {
			sum += people[i];
		}
		people[4] = total - sum; //5구역의 인구수 계산
		
		Arrays.sort(people);
		
		int diff = people[4] - people[0];
		
		if(res > diff) res = diff;
		//System.out.println(diff );
	}
	
	public static void initSection() {
		for(int i = 0; i < 5; i++) {
			people[i] = 0;
		}
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < N; j++) {
				section[i][j] = 0;
			}
		}
	}
	
	public static boolean makeSection(int r, int c, int d1, int d2) {
		initSection();
		
		//5구역 꼭지점(시계 반대방향으로)
		pr[0] = r;
		pr[1] = r+d1;
		pr[2] = r+d1+d2;
		pr[3] = r+d2;
		
		pc[0] = c;
		pc[1] = c-d1;
		pc[2] = c-d1+d2;
		pc[3] = c+d2;
		
		int cr = r;
		int cc = c;
		int nr, nc;
		int d = 0;
		
		while(true) {
			section[cr][cc] = 5; 
			
			nr = cr + ccwr[d];
			nc = cc + ccwc[d];
			
			if(!(nr >= 0 && nr < N && nc >= 0 && nc < N)) return false;
			if(nr == r && nc == c) break; //한바퀴 돌면 종료 
			if(nr == pr[d+1 > 3 ? 0 : d+1] && nc == pc[d+1 > 3 ? 0 : d+1]) {
				//경계를 만나면 방향을 바꾼다.
				d++;
			}
			cr = nr;
			cc = nc;
		}
		
		//구역의 각 모서리에서 부터 확산
		dfs(1, 0, 0);
		dfs(2, 0, N-1);
		dfs(3, N-1, 0);
		dfs(4, N-1, N-1);
		
		return true;
	}
	
	public static void dfs(int num, int r, int c) {
		if(section[r][c] != 0) return;
		else {
			section[r][c] = num;
			people[num-1] += map[r][c];
		}
		
		int nr, nc;
		for(int d = 0; d < 4; d++) {
			nr = r + dr[d];
			nc = c + dc[d];
			
			if(!(nr >= 0 && nr < N && nc >= 0 && nc < N)) continue;
			
			switch(num) {
			case 1:
				if(nr >= pr[1] || nc > pc[0]) continue;
				break;
			case 2:
				if(nr > pr[3] || nc <= pc[0]) continue;
				break;
			case 3:
				if(nr < pr[1] || nc >= pc[2]) continue;
				break;
			case 4:
				if(nr <= pr[3] || nc < pc[2]) continue;
				break;
			}
			
			dfs(num, nr, nc);
		}
	}
		
	public static void printSection() {
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < N; j++) {
				System.out.print(section[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
}
