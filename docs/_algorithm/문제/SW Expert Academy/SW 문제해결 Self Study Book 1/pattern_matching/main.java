import java.util.Scanner;
/* test data
ti
Start eating well with these eight tips for healthy eating. which cover the basics of a healthy diet and good nutrition
 */
public class main {

	public static void main(String[] args) {
		String pattern = "";
		String text = "";
		
		Scanner scanner = new Scanner(System.in);
		pattern = scanner.nextLine();
		text = scanner.nextLine();
		scanner.close();
		
		System.out.println(bruteForce(text, pattern));
	}

	private static int bruteForce(String text, String pattern) {
		int foundCnt = 0;
		
		for(int i = 0; i < text.length(); i++) {
			if(i + pattern.length() > text.length()) {
				break;
			}
			
			int j = 0;
			for(; j < pattern.length(); j++) {
				if(text.charAt(j + i) != pattern.charAt(j)) {
					break;
				}
			}
			
			if(j == pattern.length()) {
				foundCnt++;
			}
		}
		
		return foundCnt;
	}
}
