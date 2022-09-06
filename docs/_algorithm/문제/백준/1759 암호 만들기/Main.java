import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	static int L; //3~15
	static int C; //3~15
	static String cipherChars = "";
	static String tryCipher = "";
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		L = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());
		cipherChars = br.readLine().toString().replace(" ", "");
		br.close();
		
		solution();
	}
	
	private static void solution() {
		char[] cipherCharArray = cipherChars.toCharArray();
		Arrays.sort(cipherCharArray);
		cipherChars = new String(cipherCharArray);
		backtracking(0, 0, 0, 0);
	}
	
	private static void backtracking(int cnt, int idx, int vowelCnt, int consonantCnt) {
		if(cnt == L) {
			if(vowelCnt >= 1 && consonantCnt >= 2) {
				System.out.println(tryCipher);
			}
			return;
		}
		
		if(cnt > L || idx >= C) return;
		
		String temp = tryCipher;
		
		tryCipher += cipherChars.charAt(idx);
		if(isVowel(cipherChars.charAt(idx))) {
			backtracking(cnt+1, idx+1, vowelCnt+1, consonantCnt);	
		}
		else {
			backtracking(cnt+1, idx+1, vowelCnt, consonantCnt+1);
		}
		tryCipher = temp;
		
		backtracking(cnt, idx+1, vowelCnt, consonantCnt);
	}
	
	private static boolean isVowel(char c) {
		if(c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u')
			return true;
		else
			return false;
	}
}