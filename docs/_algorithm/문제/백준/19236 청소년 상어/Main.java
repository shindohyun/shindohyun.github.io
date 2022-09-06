import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static class Point {
		public int r, c;
		public Point(int r, int c) {
			this.r = r;
			this.c = c;
		}
	}
	static int[][] map = new int[4][4];
	static int[] fishDirs = new int[17]; //물고기 별 이동 방향 저장 1~16 (인덱스 0은 무시), 방향 1~8, 0은 물고기가 없는 것으로 생각
	static Point[] fishPts = new Point[17]; //물고기 별 위치 저장 1~16 (인덱스 0은 무시)
	static int sharkDir; //상어 방향 저장
	static Point sharkPt = new Point(0,0); //상어 위치 저장
	static int res = 0;

	//상|상좌|좌|하좌|하|하우|우|상우
	static int[] dr = {0,-1,-1,0,1,1,1,0,-1};
	static int[] dc = {0,0,-1,-1,-1,0,1,1,1};
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		for(int r = 0; r < 4; r++) {
			st = new StringTokenizer(br.readLine());
			for(int c = 0; c < 4; c++) {
				int idx = Integer.parseInt(st.nextToken());
				int d = Integer.parseInt(st.nextToken());
				
				map[r][c] = idx;
				fishDirs[idx] = d;
				fishPts[idx] = new Point(r, c);
			}
		}
		br.close();
		
		solution();
		System.out.println(res);
	}
	
	public static void solution() {
		//상어 입장
		int fishIdx = map[0][0];
		eatFish(fishIdx);
		
		recursive(fishIdx);
	}
	
	public static void recursive(int sum) {		
		//물고기 이동
		moveFish();
		
		//상어 이동 전에 현재 상태를 저장한다.
		int[][] copyMap = map.clone();
		for(int i = 0; i < 4; i++) {
			copyMap[i] = map[i].clone();
		}
		int[] copyFishDirs = fishDirs.clone();
		Point[] copyFishPts = new Point[17];
		for(int i = 1; i < 17; i++) {
			if(fishPts[i] != null)
				copyFishPts[i] = new Point(fishPts[i].r, fishPts[i].c);
		}
		int copySharkDir = sharkDir;
		Point copySharkPt = new Point(sharkPt.r, sharkPt.c);
		
		//상어 이동
		int nr = sharkPt.r + dr[sharkDir];
		int nc = sharkPt.c + dc[sharkDir];
		while(true) {
			if(!(nr >= 0 && nr < 4 && nc >= 0 && nc < 4)) break;
			
			int fishIdx = map[nr][nc];
			if(fishIdx != 0) {
				//물고기를 먹는다.
				eatFish(fishIdx);
				
				recursive(sum+fishIdx);
				
				//원복시킨다.
				map = copyMap.clone();
				for(int i = 0; i < 4; i++) {
					map[i] = copyMap[i].clone();
				}
				fishDirs = copyFishDirs.clone();
				for(int i = 1; i < 17; i++) {
					if(copyFishPts[i] != null) {
						fishPts[i] = new Point(copyFishPts[i].r, copyFishPts[i].c);
					}
					else {
						fishPts[i] = null;
					}
				}
				sharkDir = copySharkDir;
				sharkPt.r = copySharkPt.r;
				sharkPt.c = copySharkPt.c;
				
			}
			nr = nr + dr[sharkDir];
			nc = nc + dc[sharkDir];
		}
		
		if(res < sum) res = sum;
		return;
	}
	
	//물고기 먹기
	public static void eatFish(int fishIdx) {
		sharkDir = fishDirs[fishIdx];
		sharkPt.r = fishPts[fishIdx].r;
		sharkPt.c = fishPts[fishIdx].c;
		
		map[fishPts[fishIdx].r][fishPts[fishIdx].c] = 0;
		
		fishDirs[fishIdx] = 0;
		fishPts[fishIdx] = null;
	}
	
	//물고기 이동
	public static void moveFish() {
		for(int i = 1; i < 17; i++) {
			if(fishPts[i] == null && fishDirs[i] == 0) continue; //없는 물고기 무시
			
			Point curPt = fishPts[i];
			int curDir = fishDirs[i];
			int curIdx = i;
			
			//다음 이동 방향
			int nr = curPt.r + dr[curDir];
			int nc = curPt.c + dc[curDir];
			int nDir = curDir;
			boolean canMove = false;
			
			do {
				//다음 이동 방향 확인
				if((nr >= 0 && nr < 4 && nc >= 0 && nc < 4) && !(nr == sharkPt.r && nc == sharkPt.c)) {
					canMove = true;
					break;
				}
				else {
					//갈 수 없으면 45도 반시계로 회전
					nDir = nDir+1 > 8 ? 1 : nDir+1;
					nr = curPt.r + dr[nDir];
					nc = curPt.c + dc[nDir];
				}
			}while(nDir != curDir); //제자리로 돌아올 때까지 확인
			
			//회전한 방향으로 갱신
			fishDirs[i] = nDir;
			
			//이동할 곳이 있으면 자리 교환
			if(canMove) {
				int targetIdx = map[nr][nc];
				if(targetIdx == 0) {
					map[nr][nc] = map[curPt.r][curPt.c];
					map[curPt.r][curPt.c] = 0;
					
					fishPts[i].r = nr;
					fishPts[i].c = nc;
				}
				else {
					int tempIdx = map[nr][nc];
					map[nr][nc] = map[curPt.r][curPt.c];
					map[curPt.r][curPt.c] = tempIdx;
					
					int tempR = fishPts[targetIdx].r;
					int tempC = fishPts[targetIdx].c;
					fishPts[targetIdx].r = fishPts[i].r;
					fishPts[targetIdx].c = fishPts[i].c; 
					fishPts[i].r = tempR;
					fishPts[i].c = tempC;
				}
			}
		}
	}
	
	public static void printMap() {
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				System.out.print(map[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
}
