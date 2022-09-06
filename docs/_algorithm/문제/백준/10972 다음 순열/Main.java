import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static int N;
	static int[] p;
	
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		p = new int[N];
		StringTokenizer st = new StringTokenizer(br.readLine());
		for(int i = 0; i < N; i++) {
			p[i] = Integer.parseInt(st.nextToken());
		}
		br.close();
		
		solution();
	}
	
	private static void solution() {
		int index = -1;
		for(int i=N-1; i>0; --i) {
			if(p[i] > p[i-1]) {
				index = i-1;
				break;
			}
		}
		
		//사전순으로 마지막에 오는 순열인 경우
		if(index == -1) {
			System.out.println(-1);
			return;
		}
		
		int swapIndex = -1;
		for(int i=N-1; i > index; --i) {
			if(p[index] < p[i]) {
				swapIndex = i;
				break;
			}
		}
		
		int temp = p[index];
		p[index] = p[swapIndex];
		p[swapIndex] = temp;
		
		//뒤집기
		int start = index+1;
		int end = N-1;
		while(start < end) {
			temp = p[start];
			p[start] = p[end];
			p[end] = temp;
			start++;
			end--;
		}
		
		for(int i = 0; i < N; i++) {
			System.out.print(p[i] + " ");
		}
	}
}
