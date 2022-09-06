import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
/*
 * 			 0  1  2
 * 			 3  4  5
 * 			 6  7  8
 * 
 *  9 10 11  18 19 20  27 28 29
 * 12 13 14  21 22 23  30 31 32
 * 15 16 17  24 25 26  33 34 35
 * 			 
 *    		 36 37 38
 *    		 39 40 41
 *    		 42 43 44
 *    
 *    		 45 46 47
 *      	 48 49 50
 *      	 51 52 53
 */
public class Main {
	static int T; //~100
	static int n; //1~1000
	static char face; //회전할 면- U:위, D:아래, F:앞, B:뒤, L:왼, R:오
	static char dir; //회전 방향- +:시계, -반시계
	static char[] dice = new char[54];
	static StringBuilder sb = new StringBuilder();
	
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		T = Integer.parseInt(br.readLine());
		StringTokenizer st = null;
		for(int t=0;t<T;t++) {
			//주사위 초기화
			init();
			
			n = Integer.parseInt(br.readLine());
			st = new StringTokenizer(br.readLine());
			for(int i=0;i<n;i++) {
				String tok = st.nextToken();
				face = tok.charAt(0);
				dir = tok.charAt(1);
				
				//회전
				rotation();
			}
			
			//출력
			printUp();
		}
		br.close();
		
		System.out.println(sb);
	}
	
	//윗 면은 흰색, 아랫 면은 노란색, 앞 면은 빨간색, 뒷 면은 오렌지색, 왼쪽 면은 초록색, 오른쪽 면은 파란색이다.
	//흰색은 w, 노란색은 y, 빨간색은 r, 오렌지색은 o, 초록색은 g, 파란색은 b
	private static void init() {
		for(int i=0; i<54; i++) {
			switch(i/9) {
			case 0:
				dice[i] = 'o';
				break;
			case 1:
				dice[i] = 'g';
				break;
			case 2:
				dice[i] = 'w';
				break;
			case 3:
				dice[i] = 'b';
				break;
			case 4:
				dice[i] = 'r';
				break;
			case 5:
				dice[i] = 'y';
				break;
			}
			
		}
	}
	
	private static void rotation() {
		int rotationCnt = dir=='+'?1:3; //시계방향 3번 = 반시계 1번
		
		switch(face) {
		case 'U':
			/*     6  7  8
			 * 
			 * 11  18 19 20  27
			 * 14  21 22 23  30
			 * 17  24 25 26  33
			 * 
			 * 	   36 37 38
			 */
			for(int i=0; i<rotationCnt; i++) {
				char temp = dice[18];
				dice[18] = dice[24];
				dice[24] = dice[26];
				dice[26] = dice[20];
				dice[20] = temp;
				temp = dice [19];
				dice[19] = dice[21];
				dice[21] = dice[25];
				dice[25] = dice[23];
				dice[23] = temp;
				
				temp = dice[6];
				dice[6] = dice[17];
				dice[17] = dice[38];
				dice[38] = dice[27];
				dice[27] = temp;				
				temp = dice[7];
				dice[7] = dice[14];
				dice[14] = dice[37];
				dice[37] = dice[30];
				dice[30] = temp;
				temp = dice[8];
				dice[8] = dice[11];
				dice[11] = dice[36];
				dice[36] = dice[33];
				dice[33] = temp;
			}
			break;
		case 'D':
			/*  	 42 43 44
			 * 
			 *  15  45 46 47  35
			 *  12  48 49 50  32
			 *  9   51 52 53  29
			 *  
			 *       0  1  2
			 */
			for(int i=0; i<rotationCnt; i++) {
				char temp = dice[45];
				dice[45] = dice[51];
				dice[51] = dice[53];
				dice[53] = dice[47];
				dice[47] = temp;
				temp = dice [46];
				dice[46] = dice[48];
				dice[48] = dice[52];
				dice[52] = dice[50];
				dice[50] = temp;
				
				temp = dice[42];
				dice[42] = dice[9];
				dice[9] = dice[2];
				dice[2] = dice[35];
				dice[35] = temp;
				temp = dice[43];
				dice[43] = dice[12];
				dice[12] = dice[1];
				dice[1] = dice[32];
				dice[32] = temp;
				temp = dice[44];
				dice[44] = dice[15];
				dice[15] = dice[0];
				dice[0] = dice[29];
				dice[29] = temp;
			}
			break;
		case 'F':
			/* 	   24 25 26
			 * 
			 * 17  36 37 38  33
			 * 16  39 40 41  34
			 * 15  42 43 44  35
			 * 
			 *     45 46 47
			 */
			for(int i=0; i<rotationCnt; i++) {
				char temp = dice[36];
				dice[36] = dice[42];
				dice[42] = dice[44];
				dice[44] = dice[38];
				dice[38] = temp;
				temp = dice [37];
				dice[37] = dice[39];
				dice[39] = dice[43];
				dice[43] = dice[41];
				dice[41] = temp;
				
				temp = dice[24];
				dice[24] = dice[15];
				dice[15] = dice[47];
				dice[47] = dice[33];
				dice[33] = temp;
				temp = dice[25];
				dice[25] = dice[16];
				dice[16] = dice[46];
				dice[46] = dice[34];
				dice[34] = temp;
				temp = dice[26];
				dice[26] = dice[17];
				dice[17] = dice[45];
				dice[45] = dice[35];
				dice[35] = temp;
			}
			break;
		case 'B':
			/*
			 * 	   51 52 53
			 * 
			 * 9   0  1  2  29
			 * 10  3  4  5  28
			 * 11  6  7  8  27
			 * 
			 * 	   18 19 20
			 */
			for(int i=0; i<rotationCnt; i++) {
				char temp = dice[0];
				dice[0] = dice[6];
				dice[6] = dice[8];
				dice[8] = dice[2];
				dice[2] = temp;
				temp = dice [1];
				dice[1] = dice[3];
				dice[3] = dice[7];
				dice[7] = dice[5];
				dice[5] = temp;

				temp = dice[51];
				dice[51] = dice[11];
				dice[11] = dice[20];
				dice[20] = dice[29];
				dice[29] = temp;
				temp = dice[52];
				dice[52] = dice[10];
				dice[10] = dice[19];
				dice[19] = dice[28];
				dice[28] = temp;
				temp = dice[53];
				dice[53] = dice[9];
				dice[9] = dice[18];
				dice[18] = dice[27];
				dice[27] = temp;
			}
			break;
		case 'L':
			/*
			 *	   0 3 6
			 * 
			 * 51  9 10 11  18
			 * 48  12 13 14  21
			 * 45  15 16 17  24
			 * 
			 * 	   42 39 36
			 */
			for(int i=0; i<rotationCnt; i++) {
				char temp = dice[9];
				dice[9] = dice[15];
				dice[15] = dice[17];
				dice[17] = dice[11];
				dice[11] = temp;
				temp = dice [10];
				dice[10] = dice[12];
				dice[12] = dice[16];
				dice[16] = dice[14];
				dice[14] = temp;

				temp = dice[0];
				dice[0] = dice[45];
				dice[45] = dice[36];
				dice[36] = dice[18];
				dice[18] = temp;
				temp = dice[3];
				dice[3] = dice[48];
				dice[48] = dice[39];
				dice[39] = dice[21];
				dice[21] = temp;
				temp = dice[6];
				dice[6] = dice[51];
				dice[51] = dice[42];
				dice[42] = dice[24];
				dice[24] = temp;
			}
			break;
		case 'R':
			/*
			 *     8  5  2
			 *     
			 * 20  27 28 29  53
			 * 23  30 31 32  50
			 * 26  33 34 35  47
			 * 
			 *     38 41 44
			 */
			for(int i=0; i<rotationCnt; i++) {
				char temp = dice[27];
				dice[27] = dice[33];
				dice[33] = dice[35];
				dice[35] = dice[29];
				dice[29] = temp;
				temp = dice [28];
				dice[28] = dice[30];
				dice[30] = dice[34];
				dice[34] = dice[32];
				dice[32] = temp;

				temp = dice[8];
				dice[8] = dice[26];
				dice[26] = dice[44];
				dice[44] = dice[53];
				dice[53] = temp;
				temp = dice[5];
				dice[5] = dice[23];
				dice[23] = dice[41];
				dice[41] = dice[50];
				dice[50] = temp;
				temp = dice[2];
				dice[2] = dice[20];
				dice[20] = dice[38];
				dice[38] = dice[47];
				dice[47] = temp;
			}
			break;
		}
	}
	
	private static void printUp() {
		 for(int i=18; i<=26; i++) {
			 sb.append(dice[i]);
			 if(i==20||i==23||i==26) sb.append('\n');
		 }
	}
}

