import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class Main {
	static final int NODE_CNT = 33;
	static final int RED = 0;
	static final int BLUE = 1;
	static final int END_IDX = 21;
	
	static int[][] path = new int[NODE_CNT][2];
	static int[] score = new int[NODE_CNT];
	static int[] nums = new int[10];
	static int res = 0;
	static Set<Integer> blues = new HashSet<Integer>();
	static int[] units = new int[4]; //4개의 말 위치 저장 (노드의 인덱스 저장)
	
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		for(int i = 0; i < 10; i++) {
			nums[i] = Integer.parseInt(st.nextToken());
		}
		//RED 경로 세팅, 점수 저장
		for(int i = 0; i < END_IDX; i++) {
			path[i][RED] = i+1; //시계방향으로
			score[i] = 2*i;
		}
		score[END_IDX] = 0;
		
		path[END_IDX][RED] = END_IDX; //도착한 말은 제자리
		//십자가 왼->오, 아래->위, 점수 저
		path[22][RED] = 23;
		path[23][RED] = 24;
		path[24][RED] = 25;
		path[28][RED] = 27;
		path[27][RED] = 26;
		path[26][RED] = 25;
		path[29][RED] = 30;
		path[30][RED] = 25;
		path[25][RED] = 31;
		path[31][RED] = 32;
		path[32][RED] = 20;
		score[22] = 13;
		score[23] = 16;
		score[24] = 19;
		score[25] = 25;
		score[26] = 26;
		score[27] = 27;
		score[28] = 28;
		score[29] = 22;
		score[30] = 24;
		score[31] = 30;
		score[32] = 35;
		//BLUE 경로 세팅
		path[5][BLUE] = 22;
		path[10][BLUE] = 29;
		path[15][BLUE] = 28;
		
		//BLUE 인덱스 저장
		blues.add(5);
		blues.add(10);
		blues.add(15);
		
		
		br.close();
		
		solution();
		System.out.println(res);
	}
	
	public static void solution() {
		permutation(0, 0);
	}
	
	public static void permutation(int cnt, int sum) {
		if(cnt == 10) {
			if(res < sum) res = sum;
			return;
		}
		
		for(int i = 0; i < 4; i++) {
			int curIdx = units[i];
			
			//도착한 말은 선택하지 않는다.
			if(units[i] == END_IDX) continue;
			
			int nextIdx = move(nums[cnt], curIdx, blues.contains(curIdx));
			
			if(isOccupied(nextIdx)) {
				//선택 불가
				continue;
			}
			else {
				//이동하고 점수 누적
				units[i] = nextIdx;			
				
				permutation(cnt+1, sum+score[nextIdx]);
				
				//다른 말을 선택하는 경우를 위해 이동한 말을 원복한다.
				units[i] = curIdx;
			}
		}
	}
	
	//다른 말이 점령하고 있는 노드인지 확인한다.
	//노드가 도착 노드이면 예외
	public static boolean isOccupied(int idx) {
		if(idx == END_IDX) return false;
		
		for(int i = 0; i < 4; i++) {
			if(units[i] == idx) return true;
		}
		return false;
	}
	
	//주사위 숫자 만큼 이동한다.
	//출발 노드가 파란색인 경우 파란색 경로로 이동한다.
	public static int move(int num, int curIdx, boolean isBlue) {		
		int nextIdx = 0;
		
		if(isBlue) {
			nextIdx = path[curIdx][BLUE];
			num--;
			curIdx = nextIdx;
		}
		
		for(int i = 0; i < num; i++) {
			nextIdx = path[curIdx][RED];
			curIdx = nextIdx;
		}
		
		return nextIdx;
	}
}
