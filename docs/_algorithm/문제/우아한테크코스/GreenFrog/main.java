import java.util.Scanner;

public class main {

	public static void main(String[] args) {
		
		Scanner scan = new Scanner(System.in);
		String word = scan.nextLine();
		scan.close();
		
		char[] result = new char[word.length()]; 
				
		for(int i = 0; i < word.length(); i++) {
			char w = word.charAt(i);
			if(w != ' ') {
				if(w >= 'a' && w <= 'z') {
					w = (char)(w + 25 - ((w - 'a') * 2));
				}
				
				if(w >= 'A' && w <= 'Z') {
					w = (char)(w + 25 - ((w - 'A') * 2));
				}
				
			}
			result[i] = w;
		}
		
		System.out.println(result);
	}

}
