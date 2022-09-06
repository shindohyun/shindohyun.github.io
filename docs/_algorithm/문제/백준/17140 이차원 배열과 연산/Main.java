import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Map.Entry;

public class Main {
	static class Item{
		public int w, c; //weight, count
		public Item(int w, int c) {
			this.w = w;
			this.c = c;
		}
	}
	static class ItemComparator implements Comparator<Item>{
		public ItemComparator() {}
		@Override
		public int compare(Item o1, Item o2) {
			// TODO Auto-generated method stub
			if(o1.c == o2.c) {
				return o1.w - o2.w;
			}
			else {
				return o1.c - o2.c;
			}
		}
	}
	static int r, c, k; //1~100
	static int[][] map = new int[100][100];
	static int R = 3, C= 3;
	static int cnt = 0;
	
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		r = Integer.parseInt(st.nextToken()) - 1;
		c = Integer.parseInt(st.nextToken()) - 1;
		k = Integer.parseInt(st.nextToken());
		for(int i = 0; i < 3; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j < 3; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		br.close();
		
		solution();
		System.out.println(cnt);
	}
	
	public static void solution() {
		HashMap<Integer, Integer> check = new HashMap<Integer, Integer>();
		ArrayList<Item> temp = new ArrayList<Item>();
		ItemComparator itemComparator = new ItemComparator();
		
		while(true) {
			//printMap();

			if(map[r][c] == k) break;
			if(cnt++ > 100) { cnt = -1; break;}
			
			if(R >= C) {
				int maxC = 0;
				
				for(int i = 0; i < R; i++) {
					check.clear();
					temp.clear();
					
					//배열 아이템 중복 검사와 함께 꺼내
					for(int j = 0; j < C; j++) {
						if(map[i][j] == 0) continue;
						
						if(check.containsKey((Integer)map[i][j])) {
							check.replace((Integer)map[i][j], check.get((Integer)map[i][j])+1); 
						}
						else {
							check.put((Integer)map[i][j], 1);
						}
					}
					
					//정렬을 위해 리스트에 옮기가
					for(Entry<Integer, Integer> e : check.entrySet()) {
						temp.add(new Item(e.getKey(), e.getValue()));
					}
					
					//정렬
					Collections.sort(temp, itemComparator);
					
					//갱신
					if(maxC < temp.size()*2) maxC = temp.size()*2;
					int[] newRow = new int[100];
					int j = 0;
					for(Item item : temp) {
						newRow[j++] = item.w;
						newRow[j++] = item.c;
					}
					
					map[i] = newRow;
				}
				C = maxC;
			}
			else {
				int maxR = 0;
				
				for(int j = 0; j < C; j++) {
					check.clear();
					temp.clear();
					
					//배열 아이템 중복 검사와 함께 꺼내
					for(int i = 0; i < R; i++) {	
						if(map[i][j] == 0) continue;
						
						if(check.containsKey((Integer)map[i][j])) {
							check.replace((Integer)map[i][j], check.get((Integer)map[i][j])+1); 
						}
						else {
							check.put((Integer)map[i][j], 1);
						}
					}
					
					//정렬을 위해 리스트에 옮기가
					for(Entry<Integer, Integer> e : check.entrySet()) {
						temp.add(new Item(e.getKey(), e.getValue()));
					}
					
					//정렬
					Collections.sort(temp, itemComparator);
					
					//갱신
					if(maxR < temp.size()*2) maxR = temp.size()*2;
					int[] newCol = new int[100];
					int i = 0;
					for(Item item : temp) {
						newCol[i++] = item.w;
						newCol[i++] = item.c;
					}
					
					for(i = 0; i < 100; i++) {
						map[i][j] = newCol[i];
					}
				}
				R = maxR;
			}
		}
	}
	
	public static void printMap() {
		for(int i = 0; i < R; i++) {
			for(int j = 0; j < C; j++) {
				System.out.print(map[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
}
