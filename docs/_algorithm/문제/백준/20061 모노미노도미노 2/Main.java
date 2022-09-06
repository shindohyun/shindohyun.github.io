import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

	/*
	 * 목적: 블록을 모두 놓은 후 얻은 점수와 초록색, 파란색 보드 타일이 들어있는 칸의 개수
	 * 
	 * 조건:
	 * 1. 맵
	 * - 초록: 6x4, 파랑: 4x6
	 * 2. 블록
	 * - 횟수: N (1~10,000)
	 * - t = 1: 크기가 1×1인 블록(x, y)
	 * - t = 2: 크기가 1×2인 블록(x, y), (x, y+1)
	 * - t = 3: 크기가 2×1인 블록(x, y), (x+1, y)
	 * 3. 블록 이동
	 * - 정지 조건: 다른 블록 또른 경계를 만나는 경우
	 * - 한 줄이 꽉 차면 사라진다
	 *  > 사라진 행의 위 블록들이 사라진 행의 수 만큼 이동
	 *  > 사라진 행의 수 만큼 점수 +1
	 *  > 한 줄이 꽉 차는 경우가 없을 때 까지 반복
	 * - 연한 부분에 블록 존배하는 경우
	 *  > 블록이 있는 행의 수 만큼 아래 행이 사라진다.
	 *  > 모든 블록이 사라진 행의 수 만큼 이동
	 * 
	 * 알고리즘:
	 * 1. 초록맵, 파란맵 둘 다 6x4 맵으로 고정한다.
	 * 2. *놓여지는 블록을 초록맵, 파란맵 각가의 전용으로 만든다.
	 * 3. 특정 행 부터 위로 모든 블럭을 특정 횟수 만큼 아래로 내리는 함수 구현
	 * 4. 블록이 놓여지는 만큼 루프
	 *  1) 빨간색에 놓여지는 블록을 0~1행 사이에 둔다.
	 *  2) 다른 블록 또는 경계를 만날때 까지 아래로 내린다.
	 *  3) 한 행이 꽉 차는지 검사한다. (없을 때 까지 진행)
	 *   3-1) 아래 행 부터 탐색
	 *   3-2) 꽉 찬 행을 발견한 경우 제거한 후 점수+1, 해당 행 위 부터 모든 블록을 한 칸 씩 내린다.
	 *  4) 0, 1 행에 블록이 존재하는 경우 블록이 있는 행의 수 만큼 맨 아래 행 제거, 전체 블록을 행을 제거한 행의 수 만큼 내린다.
	 */
	// 0: (r,c)
	// 1: (r,c), (r,c+1)
	// 2: (r,c), (r+1,c)
	
	static int N;
	static int t, x, y;
	static int[][] green = new int[6][4];
	static int[][] blue = new int[6][4];
	static int score, cnt;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		N = Integer.parseInt(br.readLine());
		
		score = 0;
		cnt = 0;
		for(int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			t = Integer.parseInt(st.nextToken());
			x = Integer.parseInt(st.nextToken());
			y = Integer.parseInt(st.nextToken());
			solution();
		}
		br.close();
		
		countBlock(false);
		countBlock(true);
		
		System.out.println(score);
		System.out.println(cnt);
	}
	
	public static void solution() {
		int bx = y;
		int by = 3-x;
		
		boolean showBlue = true;
		
		setBlock(false, t, 0, y);
		setBlock(true, t, 0, by);
		//printMap(showBlue);
		
		down(false, t, 0, y);
		down(true, t, 0, by);
		//printMap(showBlue);
		
		checkFull(false, t);
		checkFull(true, t);
		//printMap(showBlue);
		
		checkTop(false);
		checkTop(true);
		//printMap(showBlue);
	}
	
	public static void countBlock(boolean isBlue) {
		int[][] map = isBlue ? blue : green;
		
		for(int i = 0; i < 6; i++) {
			for(int j = 0; j < 4; j++) {
				if(map[i][j] != 0) cnt++;
			}
		}
	}
	
	public static void checkTop(boolean isBlue) {
		int[][] map = isBlue ? blue : green;
		
		int cnt = 0;
		for(int i = 0; i <= 1; i++) {
			for(int j = 0; j < 4; j++) {
				if(map[i][j] != 0) {
					cnt++;
					break;
				}
			}
		}
		
		// 맨 아래 부터 제거
		for(int i = 5; i >= 6-cnt; i--) {
			for(int j = 0; j < 4; j++) {
				// 현재 블록이 type3 라면
				// 바로 아래 type3블록을 type1으로 변경한다.
				// 바로 아래가 type3블록이 아니라면 바로 위 블록을 type1로 변경한다.
				if(map[i][j] == 3) {
					if(i+1 < 6 && map[i+1][j] == 3) {
						map[i+1][j] = 1;
					}
					else if(i-1 >= 0 && map[i-1][j] == 3){
						map[i-1][j] = 1;
					}
				}
				map[i][j] = 0;
			}
		}
		
		int line = 5 - cnt;
		downRow(isBlue, line, cnt);
	}
	
	public static void checkFull(boolean isBlue, int type) {
		int[][] map = isBlue ? blue : green;
		if(isBlue) {
			if(type == 2) type = 3;
			else if(type == 3) type = 2;
		}
		
		while(true) {
			boolean exist = false;
			
			for(int i = 5; i >= 0; i--) {
				boolean isFull = true;
				
				for(int j = 0; j < 4; j++) {
					if(map[i][j] == 0) {
						isFull = false;
						break;
					}
				}

				if(isFull) {
					score++;
					
					// 블록 제거
					for(int j = 0; j < 4; j++) {
						// 현재 블록이 type3 라면
						// 바로 아래 type3블록을 type1으로 변경한다.
						// 바로 아래가 type3블록이 아니라면 바로 위 블록을 type1로 변경한다.
						if(map[i][j] == 3) {
							if(i+1 < 6 && map[i+1][j] == 3) {
								map[i+1][j] = 1;
							}
							else if(i-1 >= 0 && map[i-1][j] == 3){
								map[i-1][j] = 1;
							}
						}
						map[i][j] = 0;
					}
					
					// 블록 내리기
					downRow(isBlue, i-1, 1);
					exist = true;
					break;
				}
			}
			
			if(exist == false) break;
		}
	}
	
	public static void downRow(boolean isBlue, int line, int move) {
		if(line >= 5) return;
		
		int[][] map = isBlue ? blue : green;
			
		for(int i = line; i >= 0; i--) {
			for(int j = 0; j < 4; j++) {
				map[i+move][j] = map[i][j];
				map[i][j] = 0;
			}
		}
	}
	
	public static void down(boolean isBlue, int type, int r, int c) {
		int[][] map = isBlue ? blue : green;
		if(isBlue) {
			if(type == 2) type = 3;
			else if(type == 3) type = 2;
		}
		
		int cr = r;
		int nr;
		
		switch(type) {
		case 1:
		case 2:
			while(true) {
				nr = cr + 1;
				
				if(!(nr < 6)) break;
				if(type == 1) {
					if(map[nr][c] != 0) break;
				}
				else{
					if(isBlue) {
						if(map[nr][c] != 0 || map[nr][c-1] != 0) break;
					}
					else {
						if(map[nr][c] != 0 || map[nr][c+1] != 0) break;
					}
				}
				
				
				cr = nr;
			}
			
			if(type == 1) {
				map[r][c] = 0;
				map[cr][c] = type;
			}
			else {
				if(isBlue) {
					map[r][c] = 0;
					map[r][c-1] = 0;
					map[cr][c] = type;
					map[cr][c-1] = type;
				}
				else {
					map[r][c] = 0;
					map[r][c+1] = 0;
					map[cr][c] = type;
					map[cr][c+1] = type;
				}	
			}
			break;
		case 3:
			cr++;
			
			while(true) {
				nr = cr + 1;
				
				if(!(nr < 6)) break;
				if(map[nr][c] != 0) break;
				
				cr = nr;
			}
			
			map[r][c] = 0;
			map[r+1][c] = 0;
			map[cr-1][c] = type;
			map[cr][c] = type;
			break;
		}
	}
	
	public static void setBlock(boolean isBlue, int type, int r, int c) {
		int[][] map = isBlue ? blue : green;
		if(isBlue) {
			if(type == 2) type = 3;
			else if(type == 3) type = 2;
		}
		
		switch(type) {
		case 1:
			map[r][c] = type;
			break;
		case 2:
			if(isBlue) {
				map[r][c] = type;
				map[r][c-1] = type;
			}
			else {
				map[r][c] = type;
				map[r][c+1] = type;
			}
			break;
		case 3:
			map[r][c] = type;
			map[r+1][c] = type;
			break;
		}
	}
	
	public static void printMap(boolean isBlue) {
		int[][] map = isBlue ? blue : green;
		for(int i = 0; i < 6; i++) {
			for(int j = 0; j < 4; j++) {
				System.out.print(map[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
}
