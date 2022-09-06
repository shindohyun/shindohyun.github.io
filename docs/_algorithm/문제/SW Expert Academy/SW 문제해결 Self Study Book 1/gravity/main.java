import java.util.Scanner;

/* test data
9 8
7 4 2 0 0 6 0 7 0
 */
public class main {

	public static void main(String[] args) {
		int N; //가로
		int M; //세로
		int map[][] = null;
		int topRows[] = null; //각 박스 탑들의 꼭대기 저장
		
		Scanner scanner = new Scanner(System.in);
		N = scanner.nextInt();
		M = scanner.nextInt();
		map = new int[M][N];
		topRows = new int[N];
		for(int c = 0; c < N; c++) {
			int height = scanner.nextInt();
			topRows[c] = height - 1;
			for(int r = height - 1; r >= 0; r--) {
				map[r][c] = 1;
			}
		}
		scanner.close();
		
		System.out.println(getMaxHeadDrop(map, M, N));
		System.out.println(getMaxHeadDrop2(map, topRows, M, N));
	}
	
	//각 박스의 낙차 중 가장 큰 것을 찾는다.
	private static int getMaxHeadDrop(int map[][], int rowSize, int colSize) {
		int max = 0;
		int headDrop = 0;
		
		for(int c = 0; c < colSize; c++) {
			for(int r = 0; r < rowSize; r++) {
				if(map[r][c] == 1) {
					headDrop = getRightSpaceCnt(map[r], colSize, c + 1);
					if(max < headDrop) {
						max = headDrop;
					}
				}
			}
		}
		
		return max;
	}
	
	//각 박스 탑 중에서 가장 위에 있는 박스가 가장 큰 낙차를 갖는다.
	//즉 각 탑에서 가장 위에 있는 박스들의 낙차만 비교한다.
	private static int getMaxHeadDrop2(int map[][], int topRows[], int rowSize, int colSize) {
		int max = 0;
		int headDrop = 0;
		
		for(int c = 0; c < colSize; c++) {
			if(topRows[c] > 0) {
				int topRow = topRows[c];
				
				headDrop = getRightSpaceCnt(map[topRow], colSize, c + 1);
				if(max < headDrop) {
					max = headDrop;
				}
			}
		}
		
		return max;
	}
	
	//각 박스의 낙차는 해당 박스 오른쪽에 있는 공백의 개수이다.
	private static int getRightSpaceCnt(int row[], int colSize, int startCol) {
		int rightSpaceCnt = 0;
		
		for(int c = startCol; c < colSize; c++) {
			if(row[c] == 0) {
				rightSpaceCnt++;
			}
		}
		
		return rightSpaceCnt; 
	}
}
