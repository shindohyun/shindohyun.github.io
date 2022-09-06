import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Main {
	static int[] heights = new int[9];
	static boolean[] visit = new boolean[9];
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		for(int i=0;i<9;i++) {
			heights[i] = Integer.parseInt(br.readLine());
		}
		br.close();
		
		solution();
	}
	
	private static void solution() {
		combination(0, 0, 0);
	}
	
	private static boolean combination(int idx, int cnt, int sum) {
		if(cnt == 7) {
			if(sum == 100) {
				//방문한 난쟁이만 작은 순서대로 출력 출력
				ArrayList<Integer> solvList = new ArrayList<Integer>();
				for(int i=0;i<9;i++) {
					if(visit[i]) solvList.add(heights[i]);
				}
				solvList.sort(null);
				for(Integer solv : solvList) {
					System.out.println(solv);
				}
				return true;
			}
			return false;
		}
		if(!(idx < 9)) return false;
		if(visit[idx]) return false;
		
		//현재 idx를 선택하는 경우
		visit[idx] = true;
		if(combination(idx+1, cnt+1, sum+heights[idx])) return true;
		visit[idx] = false;
		
		//현재 idx를 선택하지 않는 경우
		if(combination(idx+1, cnt, sum)) return true;
		
		return false;
	}
}
