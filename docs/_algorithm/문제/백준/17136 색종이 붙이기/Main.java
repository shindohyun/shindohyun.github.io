import java.util.Scanner;

public class Main {
	static int map[][] = new int[10][10];
	static int pCnt[] = {0,5,5,5,5,5};
	static int oneCnt = 0; //1의 개수  
	static int min = Integer.MAX_VALUE;
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 10; j++) {
				int input = sc.nextInt();
				map[i][j] = input;
				if(input == 1) oneCnt++;
			}
		}
		sc.close();
		
		solution();
		System.out.println(min==Integer.MAX_VALUE? -1 : min);
	}

	private static void solution() {
		dp(0,0);
	}
	
	private static void dp(int idx, int cnt) {
		if(idx == 100) {
			min = Math.min(min, cnt);
			return;
		}
		
		if(min <= cnt) return;
		
		int r = idx/10;
		int c = idx%10;
		
		if(map[r][c] == 1) {
			for(int i = 5; i > 0; i--) {
				if(pCnt[i] > 0) {
					if(check(r, c, i)) {
						fill(r,c,i,0);
						pCnt[i]--;
						dp(idx+1, cnt+1);
						pCnt[i]++;
						fill(r,c,i,1);
					}
				}
			}
		}
		else {
			dp(idx+1, cnt);
		}
	}
	public static void fill(int r, int c, int size, int state) {
        for(int i=0; i<size; i++) {
            for(int j=0; j<size; j++) {
                map[r+i][c+j] = state;
            }
        }
    }
	
	 public static boolean check(int r, int c, int size) {
	    if(r+size>10 || c+size>10) return false;
	    for(int i=0; i<size; i++) {
	        for(int j=0; j<size; j++) {
	            if(map[r+i][c+j] != 1) return false;
	        }
	    }
	    return true;
	 }
}
