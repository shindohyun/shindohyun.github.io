import java.util.Scanner;

public class main {

	public static void main(String[] args) {
		int number = 0;
		int clap = 0;
		
		Scanner scan = new Scanner(System.in);
		number = scan.nextInt();
		scan.close();
		
		for(int i = 1; i <= number; i++) {
			clap += getClapCnt(i);
		}
		
		System.out.println(clap);
	}
	
	public static int getClapCnt(int n) {
		int cnt = 0;
		
		String strNum = Integer.toString(n);
		
		for(int i = 0; i < strNum.length(); i++) {
			char chNum = strNum.charAt(i); 
			if(		chNum == '3' || 
					chNum == '6' ||
					chNum == '9') {
				cnt++;
			}
		}
		
		return cnt;
	}

}
