import java.util.Scanner;

/* test data
4
CBBCBAAB
CCCBABCB
CAAAACAB
BACCCCAC
AABCBBAC
ACAACABC
BCCBAABC
ABBBCCAA
 */
public class main {

	public static void main(String[] args) {
		int palindromeSize = 0;
		char map[][] = new char[8][8];
		
		Scanner scanner = new Scanner(System.in);
		palindromeSize = scanner.nextInt();
		scanner.nextLine();
		for(int r = 0; r < 8; r++) {
			map[r] = scanner.nextLine().toCharArray();
		}
		scanner.close();
		
		int palindromeCnt = getPalindromeCnt(map, palindromeSize);
		System.out.println(palindromeCnt);
	}
	
	private static int getPalindromeCnt(char[][] map, int palindromeSize) {
		int palindromeCnt = 0;

		//가로방향 확인
		for(int r = 0; r < 8; r++) {
			for(int c = 0; c < 8; c++) {
				int endColIndex = c + palindromeSize - 1;
				
				if(!isWall(r, endColIndex)) { //회문 사이즈가 배열 범위를 벗어나는지 확인
					if(isPalindrome(map, c, r, true, palindromeSize)) {
						palindromeCnt++;
					}
				}
			}
		}
		
		//세로방향 확인
		for(int c = 0; c < 8; c++) {
			for(int r = 0; r < 8; r++) {
				int endRowIndex = r + palindromeSize - 1;
				
				if(!isWall(endRowIndex , c)) { //회문 사이즈가 배열 범위를 벗어나는지 확인
					if(isPalindrome(map, r, c, false, palindromeSize)) {
						palindromeCnt++;
					}
				}
			}
		}
		
		return palindromeCnt;
	}
	
	private static boolean isWall(int r, int c) {
		if(r < 0 || c < 0 || r >=8 || c >= 8) {
			return true;
		}
		else {
			return false;
		}
	}
	
	private static boolean isPalindrome(char[][] map, int startIndex, int fixIndex, boolean isRowDir, int palindromeSize) {
		boolean isPalindrome = true;
		
		//row 방향인 경우 fixIndex는 row Index
		//column 방향인 경우 fixIndex는 column Index
		//고정되는 인덱스의 한 줄만 추출하여 확인한다.
		//홀수인 경우 가운데는 항상 맞기 때문에 검사하지 않아도 된다.
		//예) 1 2 3 4 5 인 경우 회문 길이 5, 길이의 반인 2까지만 검사해도 된다.
		
		if(isRowDir) {
			char row[] = map[fixIndex];
			
			for(int cnt = 0; cnt < palindromeSize / 2; cnt++) {
				int c = startIndex +  cnt;
				int ec = startIndex + (palindromeSize - 1) - cnt;
				
				if(row[c] != row[ec]) {
					isPalindrome = false;
					break;
				}
			}
		}
		else {
			char col[] = new char[8];
			for(int i = 0; i < 8; i++) {
				col[i] = map[i][fixIndex];
			}
			
			for(int cnt = 0; cnt < palindromeSize / 2; cnt++) {
				int r = startIndex + cnt;
				int er = startIndex + (palindromeSize - 1) - cnt;
				
				if(col[r] != col[er]) {
					isPalindrome = false;
					break;
				}
			}
		}
		
		//print
		/*
		if(isPalindrome) {
			if(isRowDir) {
				System.out.print("row dir: ");	
				for(int i = 0; i < palindromeSize; i++) {
					System.out.print(map[fixIndex][i + startIndex]);
				}
				System.out.println();
			}
			else {
				System.out.print("col dir: ");	
				for(int i = 0; i < palindromeSize; i++) {
					System.out.print(map[i + startIndex][fixIndex]);
				}
				System.out.println();
			}
		}
		*/
		
		return isPalindrome;
	}
}
