import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.StringTokenizer;

public class Main {
	
	static int N; // 1~40
	static int S; //-1,000,000 ~ 1,000,000
	static int[] nums = new int[40];
	static long total = 0;
	static ArrayList<Long> front = new ArrayList<Long>();
	static ArrayList<Long> end = new ArrayList<Long>();
	
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		S = Integer.parseInt(st.nextToken());
		st = new StringTokenizer(br.readLine());
		for(int i=0; i<N; i++) {
			nums[i] = Integer.parseInt(st.nextToken());
		}
		br.close();
		
		solution();
		System.out.println(total);
	}

	private static void solution() {
		int middle = N/2;
		
		combination(true, middle, 0, 0);
		combination(false, N, middle, 0);
	
		Collections.sort(front); //오름차순
		Collections.sort(end, Comparator.reverseOrder()); //내림차순
		
		int frontIdx = 0;
		int endIdx = 0;
		
		while(true) {
			if(frontIdx >= front.size() || endIdx >= end.size()) break;
			
			long frontValue = front.get(frontIdx);
			long endValue = end.get(endIdx);
			long sum = frontValue+endValue;
			
			if(sum == S) {
				//각각의 개수를 파악
				long frontCnt = 0;
				while(frontIdx < front.size() && front.get(frontIdx) == frontValue) {
					frontCnt++;
					frontIdx++;
				}
				
				long endCnt = 0;
				while(endIdx < end.size() && end.get(endIdx) == endValue) {
					endCnt++;
					endIdx++;
				}
				
				total += (frontCnt*endCnt);
			}
			//합이 목표값 보다 크면 더하는 값을 내려주고
			else if(sum > S) {
				endIdx++;
			}
			//합이 목표값 보다 작으면 더하는 값을 올려준다.
			else if(sum < S) {
				frontIdx++;
			}
		}		
		
		if(S==0)total--; //공집합끼리의 곱은 제외 (공집합의 합은 0 인데, 목표값이 0이면 공집합의 합도 합산된다.)
	}
	
	private static void combination(final boolean isFront, final int endIdx, int idx, long sum) {
		if(idx == endIdx) {
			if(isFront) front.add(sum);
			else end.add(sum);
			return;
		}
		
		if(idx > endIdx) return;
		
		combination(isFront, endIdx, idx+1, sum);
		combination(isFront, endIdx, idx+1, sum+nums[idx]);		
	}
}
