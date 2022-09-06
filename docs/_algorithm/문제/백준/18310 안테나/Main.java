import java.util.HashMap;
import java.util.Scanner;

public class Main {
	static int N;
	static int locationSize = 0;
	static int locationArr[]; //index: 위치 번호, value: 집 위치
	static HashMap<Integer, Integer> cnts; //key: 집 위치, value: 집  개수
	static long test[][];
	static long distanceMap[][]; //거리 정보, index: 위치 번호
	static long min = 999999999;
	static int minIndex = 0;
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt();
		locationArr = new int[N];
		cnts = new HashMap<Integer, Integer>();
		
		for(int i = 0; i < N; i++) {
			int input = sc.nextInt();
			if(!cnts.containsKey(locationSize)) {
				cnts.put(input, 1);
				locationArr[locationSize++] = input;
			}
			else {
				cnts.replace(input, cnts.get(input) + 1);
			}
		}
		
		test = new long[locationSize][];
		for(int i = 0; i < locationSize; i++) {
			test[i] = new long[locationSize];
		}
		//distanceMap = new long[locationSize][locationSize];
		
		sc.close();
		
		solution();
		System.out.println(locationArr[minIndex]);
	}

	private static void solution() {
		makeDistanceMap();
		//printDistanceMap();
		
		for(int i = 0; i < locationSize; ++i) {
			long sum = 0;
			for(int j = 0; j < locationSize; ++j){
				int cnt = cnts.get(locationArr[j]);
				sum += distanceMap[i][j] * cnt;
			}
			
			if(min > sum) {
				min = sum;
				minIndex = i;
			}
		}
	}
	
	private static void makeDistanceMap() {
		for(int i = 0; i < locationSize - 1; ++i) {
			for(int j = i + 1; j < locationSize; ++j) {
				if(distanceMap[i][j] == 0) {
					int d = Math.abs(locationArr[i] - locationArr[j]);
					distanceMap[i][j] = d;
					distanceMap[j][i] = d;
				}
			}
		}
	}
	
	private static void printDistanceMap() {
		for(int i = 0; i < locationSize; i++) {
			for(int j = 0; j < locationSize; j++) {
				System.out.print(distanceMap[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
}
