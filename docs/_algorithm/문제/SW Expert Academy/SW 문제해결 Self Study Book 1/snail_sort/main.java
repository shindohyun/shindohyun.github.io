import java.util.Scanner;
/* test data
9 20 2 18 11
19 1 25 3 21
8 24 10 17 7
15 4 16 5 6
12 13 22 23 14
 */
public class main {

	public static void main(String[] args) {
		int matrixSize = 0;
		int[][] matrix = null;
		
		Scanner scanner = new Scanner(System.in);
		matrixSize = scanner.nextInt();
		matrix = new int[matrixSize][matrixSize];
		for(int i = 0; i < matrixSize; i++) {
			for(int j = 0; j < matrixSize; j++) {
				matrix[i][j] = scanner.nextInt();
			}
		}
		scanner.close();
		
		int[][] sortedMatrix = snailSort(matrix, matrixSize);
		
		for(int i = 0; i < matrixSize; i++) {
			for(int j = 0; j < matrixSize; j++) {
				System.out.print(sortedMatrix[i][j] + " ");
			}
			System.out.println();
		}
	}

	private static int[][] snailSort(int[][] matrix, int matrixSize) {
		int[][] sortedMatrix = new int[matrixSize][matrixSize];
		
		//달팽이 순회 순서: 우->하->좌->상
		//주의: 상/하 => row, 좌/우 => column
		int[] dx = {1, 0, -1, 0};
		int[] dy = {0, 1, 0, -1};
		int dirState = 0;
		
		int curX = 0;
		int curY = 0;
		int nextX = 0;
		int nextY = 0;
		
		for(int i = 0; i < matrixSize * matrixSize; i++) {
			int min = findMin(matrix, matrixSize);
			sortedMatrix[curY][curX] = min;
			
			nextX = curX + dx[dirState];
			nextY = curY + dy[dirState];
			
			//다음으로 갈 곳이 벽에 부딪히면 방향을 꺾어준다.
			//꺾어서 진행 중에 이미 채워진 곳을 만나도 방향을 꺾어준다.
			if(isWall(nextY, nextX, matrixSize) || sortedMatrix[nextY][nextX] != 0) {
				dirState = (dirState + 1) % 4;
				
				nextX = curX + dx[dirState];
				nextY = curY + dy[dirState];
			}
			
			curX = nextX;
			curY = nextY;
		}
		
		return sortedMatrix;
	}
	
	private static boolean isWall(int r, int c, int matrixSize) {
		if(r < 0 || c < 0 || r > matrixSize - 1 || c > matrixSize - 1) {
			return true;
		}
		else {
			return false;
		}
	}
	
	//2차원 배열을 탐색하면서 가장 작은 수를 찾아 반환, 가장 작은 수의 배열 위치는 적당히 큰 값으로 채워넣어 중복을 막는다.
	private static int findMin(int[][] arr, int matrixSize) {
		int min = arr[0][0];
		int minRow = 0, minCol = 0; 
		
		for(int r = 0; r < matrixSize; r++) {			
			for(int c = 0; c< matrixSize; c++) {
				if(min > arr[r][c]) {
					min = arr[r][c];
					minRow = r;
					minCol = c;
				}
			}
		}
		
		arr[minRow][minCol] = 99999;
		return min;
	}
}
