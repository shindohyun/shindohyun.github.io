import java.util.Scanner;

public class main {

	public static void main(String[] args) {
		int[] pobi = new int[2];
		int[] crong = new int[2];
		int pobiLeft = 0, pobiRight = 0, pobiMax = 0;
		int crongLeft = 0, crongRight = 0, crongMax = 0;
		
		Scanner scan = new Scanner(System.in);
		pobi[0] = scan.nextInt();
		pobi[1] = scan.nextInt();
		crong[0] = scan.nextInt();
		crong[1] = scan.nextInt();
		scan.close();
		
		if(pobi[0] + 1 != pobi[1] || crong[0] + 1 != crong[1]) {
			System.out.println(-1);
		}
		else {
			int sum = getSum(pobi[0]);
			int mul = getMul(pobi[0]);
			pobiLeft = sum > mul ? sum : mul;
			
			sum = getSum(pobi[1]);
			mul = getMul(pobi[1]);
			pobiRight = sum > mul ? sum : mul;
			
			pobiMax = pobiLeft > pobiRight ? pobiLeft : pobiRight;
			
			sum = getSum(crong[0]);
			mul = getMul(crong[0]);
			crongLeft = sum > mul ? sum : mul;
			
			sum = getSum(crong[1]);
			mul = getMul(crong[1]);
			crongRight = sum > mul ? sum : mul;
			
			crongMax = crongLeft > crongRight ? crongLeft : crongRight;
			
			if(pobiMax == crongMax) {
				System.out.println(0);
			}
			else if(pobiMax > crongMax) {
				System.out.println(1);
			}
			else if(pobiMax < crongMax) {
				System.out.println(2);
			}
		}
	}
	
	//각 자리수 합
	private static int getSum(int num) {
		int sum = 0;
		int unit = 100; //1~400 페이지 이니까
		
		while(num > 0) {
			if(num >= unit) {
				sum += num / unit;
				num = num % unit;
			}
			
			unit /= 10;
		}
		
		return sum;
	}
	
	//각 자리수 곱
	private static int getMul(int num) {
		int mul = 1;
		int unit = 100; //1~400 페이지 이니까
		
		while(num > 0) {
			if(num >= unit) {
				mul *= num / unit;
				num = num % unit;	
			}
			
			unit /= 10;
		}
		
		return mul;
	}

}
