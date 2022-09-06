import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static class Shark{
		//d: 상하우좌
		public int r, c, s, d, z;
		public Shark(int r, int c, int s, int d, int z) {
			this.r = r;
			this.c = c;
			this.s = s;
			this.d = d;
			this.z = z;
		}
	}
	static int R, C; //2~100
	static int M; //0~10000
	static Shark[][] map = new Shark[100][100];
	static int[] dr = {-1,1,0,0};
	static int[] dc = {0,0,1,-1};
	static long res = 0; //잡은 상어 무게
	
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		R = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		for(int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			int r = Integer.parseInt(st.nextToken())-1;
			int c = Integer.parseInt(st.nextToken())-1;
			int s = Integer.parseInt(st.nextToken());
			int d = Integer.parseInt(st.nextToken())-1;
			int z = Integer.parseInt(st.nextToken());
						
			map[r][c] = new Shark(r,c,s,d,z);
		}
		br.close();
		
		solution();
		System.out.println(res);
	}
	
	private static void solution() {
		for(int i = 0; i < C; i++) {
			//현재 열에서 땅과 가장 가까운 상어 잡기
			hunt(i);
			//상어 이동
			move();		
		}
	}
	
	private static void hunt(int c) {
		for(int r = 0; r < R; r++) {
			if(map[r][c] != null) {
				Shark shark = map[r][c];
				res += shark.z;
				map[r][c] = null;
				break;
			}
		}
		
	}
	
	private static void move() {		
		Shark[][] temp = new Shark[R][C];
		
		for(int r = 0; r < R; r++) {
			for(int c = 0; c < C; c++) {
				if(map[r][c] == null) continue;
				
				Shark shark = map[r][c];
				
				//상어 이동
				if(shark.d < 2) {
					int s = shark.s % (2*(R-1)); //상어 이동 계산 줄이기
					int nr, nc;
					for(int i = 0; i < s; i++) {
						nr = shark.r + dr[shark.d];
						nc = shark.c + dc[shark.d];
						
						//벽을 만나면 방향을 반대로 다시 구한다.
						if(!(nr >= 0 && nr < R)) {
							shark.d++;
							if(shark.d == 2) shark.d = 0;
							
							nr = shark.r + dr[shark.d];
							nc = shark.c + dc[shark.d];
						}
						
						shark.r = nr;
						shark.c = nc;
					}
				}
				else {
					int s = shark.s % (2*(C-1)); //상어 이동 계산 줄이기
					int nr, nc;
					for(int i = 0; i < s; i++) {
						nr = shark.r + dr[shark.d];
						nc = shark.c + dc[shark.d];
						
						//벽을 만나면 방향을 반대로 다시 구한다.
						if(!(nc >= 0 && nc < C)) {
							shark.d++;
							if(shark.d == 4) shark.d = 2;
							
							nr = shark.r + dr[shark.d];
							nc = shark.c + dc[shark.d];
						}
						
						shark.r = nr;
						shark.c = nc;
					}
				}
				
				//새로운 맵에 표시
				if(temp[shark.r][shark.c] == null || 
						(temp[shark.r][shark.c] != null && shark.z > temp[shark.r][shark.c].z)) {
					temp[shark.r][shark.c] = shark; 
				}
			}
		}
		
		//맵 교체
		map = temp;
	}
	
	private static void printMap() {
		for(int i = 0; i < R; i++) {
			for(int j = 0; j < C; j++) {
				if(map[i][j] == null) System.out.print("0 ");
				else System.out.print(map[i][j].z + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
}
