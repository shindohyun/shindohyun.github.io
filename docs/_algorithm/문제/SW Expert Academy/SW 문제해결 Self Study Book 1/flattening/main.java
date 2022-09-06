import java.util.Scanner;

/* test data
2
5 8 3 2 5 6 8 9 2 2 4
 */
public class main {

	public static void main(String[] args) {
		int dumpCnt = 0;
		int[] blockList = null;
		
		Scanner scanner = new Scanner(System.in);
		dumpCnt = scanner.nextInt();
		scanner.nextLine();
		String blockStr  = scanner.nextLine();
		String[] blockStrList = blockStr.split(" ");
		blockList = new int[blockStrList.length];
		for(int i = 0; i < blockStrList.length; i++) {
			blockList[i] = Integer.parseInt(blockStrList[i]);
		}
		scanner.close();
		
		//덤프 횟수가 끝나거나 최고점과 최저점의 차이가 1 이하인 경우 종료한다.
		int gap = 0;
		while(dumpCnt > 0) {
			gap = dump(blockList);
			if(gap <= 1) {
				break;
			}
			dumpCnt--;
		}
		
		System.out.println(gap);
	}
	
	private static int dump(int[] blockList) {
		MinMax minMax = new MinMax();
		
		//최고점에서 최저점으로 상자 하나를 이동한다.
		findMinMax(blockList, minMax);
		blockList[minMax.maxIndex]--;
		blockList[minMax.minIndex]++;
		
		//이동 후 최고점과 최저점의 차이를 구하여 반환한다.
		findMinMax(blockList, minMax);
		int gap = minMax.max - minMax.min;
		
		return gap;
	}
	
	private static void findMinMax(int[] blockList, MinMax minMax) {
		int max = blockList[0];
		int min = blockList[0];
		int maxIndex = 0;
		int minIndex = 0;
		
		for(int i = 0; i < blockList.length; i++) {
			if(blockList[i] > max) {
				max = blockList[i];
				maxIndex = i;
			}
			
			if(blockList[i] < min) {
				min = blockList[i];
				minIndex = i;
			}
		}
		
		minMax.max = max;
		minMax.min = min;
		minMax.maxIndex = maxIndex;
		minMax.minIndex = minIndex;
	}
	
	static class MinMax{
		protected int min;
		protected int max;
		protected int minIndex;
		protected int maxIndex;
		
		public MinMax() {
			
		}
	}
}
