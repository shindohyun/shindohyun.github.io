import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Scanner;

public class Main {

	static int N; //2~10
	static int personCnt[];
	static boolean map[][]; //인접 행렬
	static int min = Integer.MAX_VALUE;
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt();
		personCnt = new int[N];
		map = new boolean[N][N];
		for(int i = 0; i < N; i++) {
			personCnt[i] = sc.nextInt();
		}
		int cnt; //1~100
		for(int i = 0; i < N; i++) {
			map[i][i] = true;
			cnt = sc.nextInt();
			int ad;
			for(int j = 0; j < cnt; j++) {
				ad = sc.nextInt()-1;
				map[i][ad] = true;
			}
		}
		sc.close();
		
		solution();
		System.out.println(min == Integer.MAX_VALUE ? -1 : min);
	}
	
	private static void solution() {
		for(int i = 1; i < N; i++) {
			dp(i, 0, 0, new boolean[N]);
		}
	}
	
	//조합
	private static void dp(int cnt, int curCnt, int start, boolean visit[]) {
		if(cnt == curCnt) {
//			System.out.print(curCnt + " :");
//			for(int i = 0; i < N; i++) {
//				System.out.print(visit[i]? i : "");
//			}
			
			//해당 조합이 서로 연결되어 있는지 확인한다.
			boolean checkResult = check(visit);
//			System.out.println(" " + checkResult);
			
			return;
		}
		
		if(start == N) {
			return;
		}
		
		for(int i = start; i < N; i++) {				
			visit[i] = true;
			dp(cnt, curCnt+1, i+1, visit);
			visit[i] = false;
		}
	}
	
	private static boolean checkBfs(int root, int cnt, ArrayList<Integer> gu) {
		Deque<Integer> dq = new ArrayDeque<Integer>();
		dq.add(root);
		boolean visit[] = new boolean[N];
		int visitCnt = 0;
		visit[root] = true;
		visitCnt++;
		
		
		while(!dq.isEmpty()) {
			int cur = dq.getFirst();
			dq.removeFirst();
			for(int i = 0; i < N; i++) {
				if(i == cur) continue;
				if(map[cur][i] && visit[i] == false) {
					//인접하는 해당 지역이 같은 선거구인지 확인
					boolean isExist = false;
					for(int j = 0; j < gu.size(); j++) {
						if(i == gu.get(j))
						{
							isExist = true;
							break;
						}
					}
					if(isExist) {
						dq.addLast(i);
						visit[i] = true;
						visitCnt++;
					}
					
				}
			}
		}
		
		if(cnt == visitCnt)
			return true;
		else 
			return false;
	}
	
	private static boolean check(boolean visit[]) {
		ArrayList<Integer> gu1 = new ArrayList<Integer>();
		ArrayList<Integer> gu2 = new ArrayList<Integer>();
		
		for(int i = 0; i < N; i++) {
			if(visit[i] == true) {
				gu1.add(i);
			}
			else {
				gu2.add(i);
			}
		}
		
		//각 선거구의 지역들이 서로 연결되어있는지 확인
		if(!checkBfs(gu1.get(0), gu1.size(), gu1)) return false;
		if(!checkBfs(gu2.get(0), gu2.size(), gu2)) return false;
		
		//각 선거구가 각각 연결되어 있는 조합이라면 계산한다.
		int gu1Sum = 0;
		int gu2Sum = 0;
		for(Integer idx : gu1) {
			gu1Sum += personCnt[idx];
		}
		for(Integer idx : gu2) {
			gu2Sum += personCnt[idx];
		}
		int gap = Math.abs(gu1Sum - gu2Sum);
		if(min > gap)
			min = gap;
		
		return true;
	}
	
	private static void printMap() {
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < N; j++) {
				System.out.print(map[i][j] ? "1 " : "0 ");
			}
			System.out.println();
		}
		System.out.println();
	}
}
