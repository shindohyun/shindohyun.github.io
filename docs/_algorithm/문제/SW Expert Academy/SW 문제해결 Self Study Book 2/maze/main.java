import java.util.Scanner;
/* test code
15 15
1 1 1 1 1 1 1 1 1 1 1 1 1 1 1
1 2 0 0 0 0 1 0 0 0 1 0 0 0 1
1 1 1 1 1 0 1 1 1 0 1 0 1 0 1
1 0 0 0 1 0 0 0 1 0 0 0 1 0 1
1 0 1 1 1 0 1 1 1 1 1 0 1 1 1
1 0 0 0 1 0 0 0 0 0 0 0 0 0 1
1 1 1 0 1 1 1 1 1 1 1 0 1 0 1
1 0 0 0 0 0 0 0 1 0 0 0 1 0 1
1 1 1 0 1 1 1 0 1 1 1 0 1 0 1
1 0 1 0 0 0 1 0 0 0 0 0 1 0 1
1 0 1 0 1 0 1 1 1 1 1 1 1 0 1
1 0 0 0 1 0 1 0 1 0 0 0 0 0 1
1 0 1 0 1 0 1 0 1 0 1 0 1 1 1
1 0 1 0 1 0 0 0 0 0 1 0 0 3 1
1 1 1 1 1 1 1 1 1 1 1 1 1 1 1
 */
public class main {
	static final int delx[] = {0, 0, -1, 1};
	static final int dely[] = {-1, 1, 0, 0};
	
	public static void main(String[] args) {
		int w, h;
		int map[][];
		int check[][];
		
		Scanner scanner = new Scanner(System.in);
		w = scanner.nextInt();
		h = scanner.nextInt();
		map = new int[w][h];
		check = new int[w][h];
		for(int i = 0; i < h; i++) {
			for(int j = 0; j < w; j++) {
				map[i][j] = scanner.nextInt();
			}
		}
		scanner.close();
		
		
		System.out.println(dfs(map, check, 1, 1));
	}
	
	
	private static boolean dfs(int map[][], int check[][], int startX, int startY) {		
		if(map[startY][startX] == 3) {
			return true;
		}
		else if(map[startY][startX] != 1 && check[startY][startX] == 0) {
			check[startY][startX] = 1;
			printPath(check);
			
			int nextX;
			int nextY;
			boolean found = false;
			
			for(int i = 0; i < 4; i++) {
				nextX = startX + delx[i];
				nextY = startY + dely[i];
				
				found = dfs(map, check, nextX, nextY);
				if(found == true) {
					break;
				}
			}
			
			return found;
		}
		else {
			return false;
		}
	}

	
	private static void printPath(int check[][]) {
		for(int row[] : check) {
			for(int cell : row) {
				System.out.print(cell + " ");
			}
			System.out.println();
		}
		
		System.out.println();
	}
}
