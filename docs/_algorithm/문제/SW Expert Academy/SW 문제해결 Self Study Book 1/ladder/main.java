import java.util.Scanner;
/* test data
10
1 0 0 0 1 0 1 0 0 1
1 0 0 0 1 0 1 1 1 1
1 0 0 0 1 0 1 0 0 1
1 0 0 0 1 1 1 0 0 1
1 0 0 0 1 0 1 0 0 1
1 1 1 1 1 0 1 1 1 1
1 0 0 0 1 0 1 0 0 1
1 1 1 1 1 0 1 0 0 1
1 0 0 0 1 1 1 0 0 1
1 0 0 0 1 0 1 0 0 2
 */
public class main {

	public static void main(String[] args) {
		int mapSize = 0;
		int[][] map = null;
		
		Scanner scanner = new Scanner(System.in);
		//도착지점 부터 거꾸로 올라가면서 경로를 따라 간다.
		//이때 경로의 좌우를 보면서 이동할 곳이 있는지 본다. 때문에 경계선의 여유 공간을 두어
		//탐색이 편하게 한다. 여유 공간을 주지 않는 경우 탐색 과정에서 인덱스의 변화에 따라 
		//맵의 경계선인지 즉, 배열의 범위인지를 확인해야한다.
		//진짜 맵의 마지막 인덱스는 배열 크기 - 2 (원래 인덱스는 배열 크기의 -1 인데 마지막은 경계선이니까 하나 더 뺀다.)
		mapSize = scanner.nextInt() + 2;
		map = new int[mapSize][mapSize];
		for(int r = 1; r < mapSize - 1; r++) {
			for(int c = 1; c < mapSize - 1; c++) {
				map[r][c] = scanner.nextInt();
			}
		}
		scanner.close();

		int targetCol = findTargetColumn(map, mapSize);
		int startCol = findStartColumn(map, mapSize, targetCol);
		
		System.out.println(startCol - 1);
	}
	
	private static int findTargetColumn(int[][] map, int mapSize) {
		int targetCol = 0;
		
		for(int c = 1; c < mapSize - 1; c++) {
			if(map[mapSize - 2][c] == 2) {
				targetCol = c;
				break;
			}
		}
		
		return targetCol;
	}
	
	private static int findStartColumn(int[][] map, int mapSize, int targetCol) {
		int startCol = 0;
		
		int r = mapSize - 2;
		int c = targetCol;
		
		//좌우 보면서 이동할 수 있을때 까지 이동(사다리가 없을 때 까지)
		//끝까지 이동 후 위로 이동(위로 이동 안하면 다시 반대 방향으로 이동됨)
		//없으면 위로 이동
		while(r > 1) {
			//좌로 갈 수 있는 경우
			if(map[r][c - 1] == 1) {
				while(map[r][c - 1] != 0) { //갈 수 있을때 까지 간다.
					c--;
				}
				r--;
			}
			//우로 갈 수 있는 경우
			else if(map[r][c + 1] == 1) {
				while(map[r][c + 1] != 0) { //갈 수 있을때 까지 간다.
					c++;
				}
				r--;
			}
			//위로 이동
			else {
				r--;
			}
		}
		
		startCol = c;
		
		return startCol;
	}
}
