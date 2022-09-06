import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main {
	static String[] gs = new String[4];
	static int K; //1~100
	static int sum = 0;
	
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		for(int i = 0; i < 4; i++) {
			gs[i] = br.readLine();	
		}
		K = Integer.parseInt(br.readLine());
		StringTokenizer st = null;
		for(int i = 0; i < K; i++) {
			st = new StringTokenizer(br.readLine());
			rotation(Integer.parseInt(st.nextToken())-1, Integer.parseInt(st.nextToken()));
		}
		br.close();
	
		calculate();
		System.out.println(sum);
	}
	
	private static void calculate() {
		for(int i = 0; i < 4; i++) {
			if(gs[i].charAt(0) == '1') {
				sum += Math.pow(2, i);
			}
		}
	}
	
	private static void rotation(int idx, int d) {
		int[] idxs = new int[4];
		int[] ds = new int[4];		
		idxs[0] = idx;
		ds[0] = d;
		int rotCnt = 1;
		
		int curIdx = idx;
		int curD = d;
		int leftIdx;
		//왼쪽 톱니바퀴 탐색
		while(true) {
			leftIdx = curIdx-1;
			if(leftIdx < 0) break;
			if(gs[leftIdx].charAt(2) != gs[curIdx].charAt(6)) {
				idxs[rotCnt] = leftIdx;
				ds[rotCnt] = curD * -1;
				rotCnt++;
				curIdx = leftIdx;
				curD = curD*-1;
			}
			else {
				break;
			}
		}
		
		curIdx = idx;
		curD = d;
		int rightIdx;
		//오른쪽 톱니바퀴 탐색
		while(true) {
			rightIdx = curIdx+1;
			if(rightIdx >= 4) break;
			if(gs[rightIdx].charAt(6) != gs[curIdx].charAt(2)) {
				idxs[rotCnt] = rightIdx;
				ds[rotCnt] = curD * -1;
				rotCnt++;
				curIdx = rightIdx;
				curD = curD*-1;
			}
			else {
				break;
			}
		}
		
		for(int i = 0; i < rotCnt; i++) {
			rotationEx(idxs[i], ds[i]);	
		}
	}
	
	private static void rotationEx(int idx, int d) {
		char[] g = gs[idx].toCharArray();
		
		if(d==1) {
			char temp = g[7];
			g[7] = g[6];
			g[6] = g[5];
			g[5] = g[4];
			g[4] = g[3];
			g[3] = g[2];
			g[2] = g[1];
			g[1] = g[0];
			g[0] = temp;
		}
		else {
			char temp = g[0];
			g[0] = g[1];
			g[1] = g[2];
			g[2] = g[3];
			g[3] = g[4];
			g[4] = g[5];
			g[5] = g[6];
			g[6] = g[7];
			g[7] = temp;
		}
				
		gs[idx] = new String(g);
	}
}
