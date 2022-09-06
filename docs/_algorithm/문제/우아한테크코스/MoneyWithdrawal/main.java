import java.util.Scanner;

public class main {
	public static void main(String[] args) {
		final int units[] = {50000, 10000, 5000, 1000, 500, 100, 50, 10, 1};
		int cnts[] = new int[units.length];
		int money = 0;
		int unitCnt = 0;
		
		Scanner scan = new Scanner(System.in);
		money = scan.nextInt();
		scan.close();
		
		for(int i = 0; i < units.length; i++) {
			unitCnt = money / units[i];
			if(unitCnt > 0) {
				cnts[i] = unitCnt;
				money = money % units[i];
			}
		}
		
		for(int i = 0; i < units.length; i++) {
			System.out.println(units[i] + "원 " + cnts[i] + "개");
		}
	}
}
