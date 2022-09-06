import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.StringTokenizer;

public class Main {
	static int N; // 1~10000
	static int[][] green = new int[6][4];
	static int[][] blue = new int[6][4];
	static int idx3 = 3;
	static int score = 0;
	static int tileCnt = 0;
	
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		StringTokenizer st = null;
		for(int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			int green_t = Integer.parseInt(st.nextToken());
			int green_r = Integer.parseInt(st.nextToken());
			int green_c = Integer.parseInt(st.nextToken());
			
			solution(true, green, green_t, green_r, green_c);
			
			int blue_t = 0;
			int blue_r = 0;
			int blue_c = 0;
			
			switch(green_t) {
			case 1: 
				blue_t = 1; 
				blue_r = green_c;
				blue_c = 4-green_r-1;
				break;
			case 2: 
				blue_t = 3;
				blue_r = green_c;
				blue_c = 4-green_r-1;
				break;
			case 3: 
				blue_t = 2; 
				blue_r = green_c;
				blue_c = 4-green_r-1-1;
				break;
			}
			
			
			solution(false, blue, blue_t, blue_r, blue_c);
		}
		br.close();
		
		// 타일 개수
		for(int i = 0; i < 2; i++) {
			int[][] map = i == 0 ? green : blue;
			for(int r = 0; r < 6; r++) {
				for(int c = 0; c < 4; c++) {
					if(map[r][c] != 0) tileCnt++;
				}
			}
		}
		System.out.println(score);
		System.out.println(tileCnt);
	}
	
	public static void solution(boolean isGreen, int[][] map, int t, int r, int c) {
		// 준비
		switch(t) {
		case 1:
			map[0][c] = 1;
			break;
		case 2:
			map[0][c] = 2;
			map[0][c+1] = 2;
			break;
		case 3:
			map[0][c] = idx3;
			map[1][c] = idx3;
			idx3++;
			break;
		}
		
		down(map);
		
		// 행 제거하고 내리기(사라지는 행이 없을때 까지 반복)
		boolean remove = false;
		do {
			remove = false;
			// 사라지는 행이 있는지 검사한다.
			for(int i = 5; i >= 0; i--) {
				boolean removeRow = true;
				for(int j = 0; j < 4; j++) {
					if(map[i][j] == 0) {
						removeRow = false;
						break;
					}
				}
				
				if(removeRow) {
					remove = true;
					score++;
					// 행 제거
					for(int j = 0; j < 4; j++) {
						if(map[i][j] >= 3) {
							if(i-1 >= 0 && map[i-1][j] == map[i][j])
								map[i-1][j] = 1; 
							else if(i+1 < 6 && map[i+1][j] == map[i][j])
								map[i+1][j] = 1;
						}
						
						map[i][j] = 0;
					}
				}
			}
			
			if(remove) {
				down(map);
			}
		}while(remove);
		
		// 0~1 행에 블록 있으면 행 개수 만큼 밑에 제거
		int noneEmptyCnt = 0;
		for(int i = 1; i >= 0; i--) {
			boolean emptyRow = true;
			for(int j = 0; j < 4; j++) {
				if(map[i][j] != 0) {
					emptyRow = false;
					break;
				}
			}
			if(!emptyRow) {
				noneEmptyCnt++;
			}
		}
		
		int removeRow = 5;
		for(int i = 0; i < noneEmptyCnt; i++) {
			for(int j = 0; j < 4; j++) {
				if(map[removeRow][j] >= 3) {
					if(removeRow-1 >= 0 && map[removeRow-1][j] == map[removeRow][j])
						map[removeRow-1][j] = 1; 
					else if(removeRow+1 < 6 && map[removeRow+1][j] == map[removeRow][j])
						map[removeRow+1][j] = 1;
				}
				
				map[removeRow][j] = 0;
			}
			removeRow--;
		}
		if(noneEmptyCnt > 0) down(map);
		
//		printMap(isGreen, map);
	}
	
	public static void down(int[][] map) {
		for(int r = 5; r >= 0; r--) {
			for(int c = 0; c < 4; c++) {
				int t = map[r][c];
				
				if(t == 1) {
					int cr = r;
					int nr = 0;
					while(true){
						nr = cr + 1;
						
						if(!(nr < 6)) break;
						if(map[nr][c] != 0) break;
						cr = nr;
					}
					
					if(cr != r) {
						map[cr][c] = map[r][c];
						map[r][c] = 0;
					}
				}
				else if(t == 2 && c+1 < 4 && map[r][c+1] == 2) {
					int cr = r;
					int nr = 0;
					while(true){
						nr = cr + 1;
						
						if(!(nr < 6)) break;
						if(map[nr][c] != 0 || map[nr][c+1] != 0) break;
						cr = nr;
					}
					
					if(cr != r) {
						int temp = map[r][c];
						map[r][c] = 0;
						map[r][c+1] = 0;
						map[cr][c] = temp;
						map[cr][c+1] = temp;
					}
				}
				else if(t >= 3 && r-1 >= 0 && map[r-1][c] >= 3) {
					int cr = r;
					int nr = 0;
					while(true){
						nr = cr + 1;
						
						if(!(nr < 6)) break;
						if(map[nr][c] != 0) break;
						cr = nr;
					}
					
					if(cr != r) {
						int temp = map[r][c];
						map[r][c] = 0;
						map[r-1][c] = 0;
						map[cr][c] = temp;
						map[cr-1][c] = temp;
					}
				}
			}
		}
	}
	
	public static void printMap(boolean isGreen, int[][] map) {
//		if(!isGreen) return;
		System.out.println(isGreen ? "green" : "blue");
		for(int i = 0; i < 6; i++) {
			for(int j = 0; j < 4; j++) {
				System.out.print(map[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
}