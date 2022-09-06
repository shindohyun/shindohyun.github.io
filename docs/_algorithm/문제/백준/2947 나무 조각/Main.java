import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static int[] blocks = new int[5];
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		for(int i = 0; i < 5; i++) {
			blocks[i] = Integer.parseInt(st.nextToken());
		}
		br.close();
		
		solution();
	}
	
	public static void solution() {
		while(true) {
			boolean res = true;
			for(int i = 1; i <= 5; i++) {
				if(blocks[i-1] != i) {
					res = false;
					break;
				}
			}
			if(res) break;
			
			if(blocks[0] > blocks[1]) {
				swap(0, 1);
				printMap();
			}
			
			if(blocks[1] > blocks[2]) {
				swap(1, 2);
				printMap();
			}
			
			if(blocks[2] > blocks[3]) {
				swap(2, 3);
				printMap();
			}
			
			if(blocks[3] > blocks[4]) {
				swap(3, 4);
				printMap();
			}	
		}
	}
	public static void printMap() {
		for(int i = 0; i < 5; i++) {
			System.out.print(blocks[i] + " ");
		}
		System.out.println();
	}
	public static void swap(int i1, int i2) {
		int temp = blocks[i1];
		blocks[i1] = blocks[i2];
		blocks[i2] = temp;
	}
}
