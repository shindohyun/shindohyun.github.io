import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	static class Point{
		public int r, c;
		public Point(int r, int c) {
			this.r = r;
			this.c = c;
		}
	}
	
	static int N; //2~50
	static int M; //1~13
	static int[][] map = new int[50][50];
	static Point[] chickens = new Point[13];
	static int chickenCnt = 0;
	static int min = Integer.MAX_VALUE;
	
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		for(int i=0; i<N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0; j<N; j++) {
				int input = Integer.parseInt(st.nextToken());
				if(input == 2) {
					chickens[chickenCnt++] = new Point(i,j);
				}
				else {
					map[i][j] = input;
				}
			}
		}
		br.close();
		
		solution();
		System.out.println(min);
	}
	
	private static void solution() {
		combination(0, 0);
	}
	
	private static void combination(int cnt, int idx) {
		if(cnt == M) {
			int total = 0;
			for(int i=0; i<N; i++) {
				for(int j=0; j<N; j++) {
					if(map[i][j] != 1) continue;
					
					int minDis = Integer.MAX_VALUE;
					
					for(int z=0; z<chickenCnt; z++) {
						if(map[chickens[z].r][chickens[z].c]!=2) continue;
						int dis = Math.abs(i-chickens[z].r) + Math.abs(j-chickens[z].c);
						if(dis < minDis) minDis=dis;
					}
					
					total += minDis;
				}
			}
			
			if(min > total) min = total;
			
			return;
		}
		
		if(cnt > M || idx >= chickenCnt || map[chickens[idx].r][chickens[idx].c]==2) return;
		
		map[chickens[idx].r][chickens[idx].c] = 2;
		combination(cnt+1, idx+1);
		map[chickens[idx].r][chickens[idx].c] = 0;
		
		combination(cnt, idx+1);
	}
	
	private static void printMap() {
		for(int i = 0; i < N; i++) {
			for(int j=0; j< N; j++) {
				System.out.print(map[i][j]+" ");
			}
			System.out.println();
		}
		System.out.println();
	}
}
