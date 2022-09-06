import java.util.Scanner;

public class Main {

	static int N, M;
	static int map[][];
	static int min = Integer.MAX_VALUE;
	static int islandCnt = 0;
	static final int dr[] = {-1,1,0,0};
	static final int dc[] = {0,0,-1,1};
	static int ad[][];
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt();
		M = sc.nextInt();
		map = new int[N][M];
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < M; j++) {
				int input = sc.nextInt();
				map[i][j] = input == 1 ? -1 : input;
			}
		}
		sc.close();
		
		solution();
		System.out.println(min == Integer.MAX_VALUE ? -1 : min);
	}
	
	private static void solution() {
		giveNum();
		//printMap();
		makeBridge();
		//printAd();
		calculate();
	}
	
	private static void makeBridge() {
		//가로 다리
		int left=0, right=0;
		boolean measureFlag = false;
		int bridgeLength = 0;
		
		for(int r = 0; r < N; r++) {
			measureFlag = false;
			bridgeLength = 0;
			
			for(int c = 0; c < M; c++) {
				if(map[r][c] == 0) {
					if(measureFlag == false) {
						left = c - 1;
						if(left >= 0 && map[r][left] != 0) {
							measureFlag = true; //다리 길이 측정 시작
							bridgeLength++;
						}
					}
					else {
						bridgeLength++;
						right = c + 1;
						if(right < M && map[r][right] != 0) {
							//측정 종료
							measureFlag = false;
							//시작과 끝이 같지 않고 2 이상이고 기존값 보다 작으면 갱신한다.
							if( map[r][left] != map[r][right] &&
								bridgeLength >= 2 && 
								(ad[map[r][left] - 1][map[r][right] - 1] == 0 || ad[map[r][left] - 1][map[r][right] - 1] > bridgeLength)) {
								ad[map[r][left] - 1][map[r][right] - 1] = bridgeLength;
								ad[map[r][right] - 1][map[r][left] - 1] = bridgeLength;
							}
							
							bridgeLength = 0;
						}
					}	
				}
				else {
					if(measureFlag == true) {
						measureFlag = false;
						bridgeLength = 0;
					}
				}
			}
		}

		//세로 다리
		int top=0, bottom=0;
		measureFlag = false;
		bridgeLength = 0;
		
		for(int c = 0; c < M; c++) {
			measureFlag = false;
			bridgeLength = 0;
			
			for(int r = 0; r < N; r++) {
				if(map[r][c] == 0) {
					if(measureFlag == false) {
						top = r - 1;
						if(top >= 0 && map[top][c] != 0) {
							measureFlag = true; //다리 길이 측정 시작
							bridgeLength++;
						}
					}
					else {
						bridgeLength++;
						bottom = r + 1;
						if(bottom < N && map[bottom][c] != 0) {
							//측정 종료
							measureFlag = false;
							//시작과 끝이 같지 않고 2 이상이고 기존값 보다 작으면 갱신한다.
							if( map[top][c] != map[bottom][c] &&
								bridgeLength >= 2 && 
								(ad[map[top][c] - 1][map[bottom][c] - 1] == 0 || ad[map[top][c] - 1][map[bottom][c] - 1] > bridgeLength)) {
								ad[map[top][c] - 1][map[bottom][c] - 1] = bridgeLength;
								ad[map[bottom][c] - 1][map[top][c] - 1] = bridgeLength;
							}
							
							bridgeLength = 0;
						}
					}	
				}
				else {
					if(measureFlag == true) {
						measureFlag = false;
						bridgeLength = 0;
					}
				}
			}
		}
	}
	
	//최소신장트리, 프림 알고리즘
	private static void calculate() {		
		int visitCnt = 0;
		
		boolean visit[] = new boolean[islandCnt];
		visit[0] = true;
		visitCnt++;
		
		int sum = 0;
		final int MAX_VALUE = 999999999;
		int lineCnt = 0; //간선 개수
		
		while(visitCnt < islandCnt) {
			int minValue = MAX_VALUE;
			int minCol = 0;
						
			//최소값 찾기
			for(int i = 0; i < islandCnt; i++) {
				if(visit[i] == false) continue;
				
				for(int c = 0; c < islandCnt; c++) {
					if(ad[i][c] != 0 && visit[c] == false) {
						if(minValue > ad[i][c]) {
							minValue = ad[i][c];
							minCol = c;
						}
					}
				}
			}
			
			if(minValue != MAX_VALUE) {
				sum += minValue;
				lineCnt++;
			}
				
			visit[minCol] = true;
			visitCnt++;
		}
		
		if(lineCnt != islandCnt-1) return; //최소신장트리의 간선 개수는 노드 수 -1
		
		if(sum != MAX_VALUE && sum != 0 && min > sum) {
			min = sum;
		}
	}
	
	private static void printAd() {
		for(int i = 0; i < ad.length; i++) {
			for(int j = 0; j < ad.length; j++) {
				System.out.print(ad[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	private static void giveNum() {
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < M; j++) {
				if(map[i][j] == -1) {
					islandCnt++;
					giveNumEx(i,j);
				}
			}
		}
		
		//섬들의 관계를 표현하는 인접행렬을 만들어 섬들을 잇는 다리의 최소값을 저장한다.
		ad = new int[islandCnt][islandCnt];
	}
	
	private static void giveNumEx(int cr, int cc) {
		if(map[cr][cc] == islandCnt) return;
		
		map[cr][cc] = islandCnt;
		
		int nr,nc;
		for(int d=0;d<4;d++) {
			nr = cr + dr[d];
			nc = cc + dc[d];
			
			if(!(nr >= 0 && nr < N && nc >= 0 && nc < M) || map[nr][nc] != -1) continue;
			
			giveNumEx(nr, nc);
		}
	}
	
	private static void printMap() {
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < M; j++) {
				System.out.print(map[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
}
