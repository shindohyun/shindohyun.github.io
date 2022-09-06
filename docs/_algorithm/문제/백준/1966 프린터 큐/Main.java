import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	static int T; 
	static int N; // 1~100
	static int M; // 0~99
	static PriorityQueue<Integer> pq;
	static Queue<Number> q;
	static int res;
	
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		T = Integer.parseInt(br.readLine());
		for(int i = 0; i < T; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
			st = new StringTokenizer(br.readLine());
			pq = new PriorityQueue<Integer>(Collections.reverseOrder());
			q = new LinkedList<Main.Number>();
			for(int j = 0; j < N; j++) {
				int w = Integer.parseInt(st.nextToken());
				pq.add(w);
				q.add(new Number(j, w));
			}
			solution();
			System.out.println(res);
		}
		br.close();
	}
	
	public static void solution() {
		res = 1;
		
		Integer w = pq.poll();
		
		while(!q.isEmpty()) {
			Number n = q.poll();
			
			if(n.w < w) {
				q.add(n);
			}
			else if(n.w == w){
				if(n.idx == M) {
					return;
				}
				w = pq.poll();
				res++;
			}
		}
	}
	
	static class Number{
		public int idx, w;
		public Number(int idx, int w) {
			this.idx = idx;
			this.w = w;
		}
	}
}
