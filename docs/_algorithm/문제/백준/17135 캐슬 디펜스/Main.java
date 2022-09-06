import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	static int N, M;
	static int map[][];
	static int D;
	static int kill = 0; //궁수에 죽은 적의 수
	static int cnt = 0; //총 적의 수
	static int archerCols[] = new int[3];
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt();
		M = sc.nextInt();
		D = sc.nextInt();
		map = new int[N][M];
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < M; j++) {
				int input = sc.nextInt();
				if(input == 1)cnt++;
				map[i][j] = input;
			}
		}
		sc.close();
		
		solution();
		System.out.println(kill);
	}

	private static void solution() {
		setting(0, 0);
	}
	
	//궁수를 배치한다.
	private static void setting(int startCol, int aCnt) {
		if(aCnt >= 3) {
			//배치가 완료되면 시뮬레이션
			//printState();
			simulation();
			return;
		}
		
		if(startCol >= M) {
			return;
		}

		for(int c = startCol; c < M; c++) {
			archerCols[aCnt] = c;
			setting(c + 1, aCnt + 1);
			archerCols[aCnt] = 0;
		}
	}
	
	private static void simulation() {
		int simulMap[][] = new int[N][M];
		for(int i = 0; i < N; i++) {
			simulMap[i] = map[i].clone();
		}
		
		int simulKill = 0;
		int simulCnt = cnt;
		
		
		while(true) {
			//공격
			int curKillCnt = attack(simulMap);
			//공격 당한 적의 수 증가, 총 적의 수 감소
			simulKill = simulKill + curKillCnt;
			simulCnt = simulCnt - curKillCnt;
			if(simulCnt == 0) break;
			
			//적 이동
			int dieCnt = move(simulMap);
			//성에 도착한 적의 수 만큼 총 적의 수 감소
			simulCnt = simulCnt - dieCnt;
			if(simulCnt == 0) break;
		}
		
		if(kill < simulKill) {
			kill = simulKill;
		}
	}
	
	//각 궁수의 사정거리에서 가장 가까운 적 중에서 가장 왼쪽 적을 찾는다.
	//주의: 모든 궁수는 동시에 공격하기 때문에 선택 당하는 적의 경우가 최적일 수 없다. 무조건 위 규칙대로 선택당한다.
	//죽인 적의 수 반환
	private static int attack(int[][] simulMap) {
		int killCnt = 0;
		int targets_r[] = new int[N*M];
		int targets_c[] = new int[N*M];
		
		for(int a = 0; a < archerCols.length; ++a) {
			boolean kill = false;
			
			for(int d = 1; d <= D; d++) {
				for(int c = 0; c < M; ++c) {
					for(int r = N-1; r >= 0; --r) {
						if(simulMap[r][c] == 1 && getDistance(N, r, archerCols[a], c) == d) {
							boolean dupli = false;
							for(int i = 0; i < killCnt; i++) {
								if(targets_r[i] == r && targets_c[i] == c) {
									dupli = true;
								}
							}
							
							
							targets_r[killCnt] = r;
							targets_c[killCnt] = c;
							if(!dupli)killCnt++;
							kill = true;
							break;
						}
						if(kill)break;
					}
					if(kill)break;
				}
				if(kill)break;
			}	
		}

		for(int i = 0; i < killCnt; i++) {
			simulMap[targets_r[i]][targets_c[i]] = 0;
		}
		
		//printMap(simulMap);
		
		return killCnt;
	}
	
	//한 칸씩 내린다.
	//성에 도착하는 적의 수를 반환
	private static int move(int[][] simulMap) {
		int[] temp = null;
		int[] temp2 = null;
		
		for(int r = 0; r < N; r++) {
			if(r == 0) {
				temp = simulMap[r].clone();
				simulMap[r] = new int[M];	
			}
			else {
				temp2 = simulMap[r].clone();
				simulMap[r] = temp.clone();
				temp = temp2.clone();
			}
		}
		
		int dieCnt = 0;
		for(int c = 0; c < M; c++) {
			if(temp[c] == 1) {
				dieCnt++;
			}
		}
		
		//printMap(simulMap);
		
		return dieCnt;
	}
	
	private static int getDistance(int r1, int r2, int c1, int c2) {
		return Math.abs(r1-r2) + Math.abs(c1 - c2);
	}
	
	private static void printState() {
		for(int i = 0; i < archerCols.length; i++) {
			System.out.print(archerCols[i] + " ");
		}
		System.out.println();
	}
	private static void printMap(int printMap[][]) {
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < M; j++) {
				System.out.print(printMap[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
}
