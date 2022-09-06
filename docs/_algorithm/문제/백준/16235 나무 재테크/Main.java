import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
	static int N; // 1~10
	static int M; // 1~100
	static int K; // 1~1000
	static int[][] map = new int[10][10];
	static int[][] addMap = new int[10][10];
	static int[][] deathMap = new int[10][10];
	static ArrayList<Integer>[][] treeMap = new ArrayList[10][10];
	static int[] dr = {1,-1,0,0,1,-1,1,-1};
	static int[] dc = {0,0,-1,1,1,1,-1,-1};
	static int treeCnt;
	static int res = 0;
	
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		treeCnt = M;
		K = Integer.parseInt(st.nextToken());
		for(int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j < N; j++) {
				map[i][j] = 5;
				addMap[i][j] = Integer.parseInt(st.nextToken());
				treeMap[i][j] = new ArrayList<Integer>();
			}
		}
		for(int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());
			int z = Integer.parseInt(st.nextToken());
			treeMap[x-1][y-1].add(z);
		}
		br.close();
		
		solution();
		System.out.println(res=treeCnt);
	}
	
	private static void solution() {
		for(int i = 0; i < K; i++) {
			spring();
			summer();
			fall();
			winter();
		}
	}
	private static void spring() {
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < N; j++) {
				if(treeMap[i][j].size() == 0) continue;
				
				ArrayList<Integer> trees = treeMap[i][j];
				final int TREE_CNT = trees.size();
				int deathIdx = -1;
				Collections.sort(trees);
				
				for(int k = 0; k < TREE_CNT; k++) {
					if(map[i][j] < trees.get(k)) {
						deathIdx = k;
						break;
					}
					else {
						map[i][j] -= trees.get(k);
						trees.set(k, trees.get(k)+1);
					}
				}
				
				// 이 나무를 죽여 양분을 얻는 부분의 처리가 중요
				if(deathIdx != -1) {
					for(int k = 0; k < TREE_CNT-deathIdx; k++) {
						deathMap[i][j] += (trees.get(deathIdx)/2);
						trees.remove(deathIdx);
						treeCnt--;
					}
				}
			}
		}
	}
	private static void summer() {
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < N; j++) {
				if(deathMap[i][j] == 0) continue;
				map[i][j] += deathMap[i][j];
				deathMap[i][j] = 0;
			}
		}
	}
	private static void fall() {
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < N; j++) {
				if(treeMap[i][j].size() == 0) continue;
				
				ArrayList<Integer> trees = treeMap[i][j];
				for(int k = 0; k < trees.size(); k++) {
					if(trees.get(k) % 5 != 0) continue;
					
					int nr, nc;
					for(int d = 0; d < 8; d++) {
						nr = i+dr[d];
						nc = j+dc[d];
						
						if(!(nr >= 0 && nr < N && nc >= 0 && nc < N)) continue;
						
						treeMap[nr][nc].add(1);
						treeCnt++;
					}
				}
			}
		}
	}
	private static void winter() {
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < N; j++) {
				map[i][j] += addMap[i][j];
			}
		}
	}
}
