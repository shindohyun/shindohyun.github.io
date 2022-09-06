import java.util.Scanner;

public class main {

	public static void main(String[] args) {
		String cryptogram;
		
		Scanner scan = new Scanner(System.in);
		cryptogram = scan.nextLine();
		scan.close();
		
		int dupliIndex = -1;
		while(true) {
			dupliIndex = checkDupli(cryptogram);
			
			if(dupliIndex == -1) {
				break;
			}
			else {
				StringBuffer temp = new StringBuffer(cryptogram);
				temp.delete(dupliIndex - 1, dupliIndex + 1);
				cryptogram = new String(temp);
			}
		}
		
		System.out.println(cryptogram);
	}
	
	public static int checkDupli(String str) {
		int dupliIndex = -1;
		char pre = 0;
		char check = 0;
		
		
		for(int i = 0; i < str.length(); i++) {
			check = str.charAt(i);
			
			if(pre == check) {
				dupliIndex = i;
				break;
			}
			
			pre = check;
		}
		
		return dupliIndex;
	}
}
