import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
	static int N;
	static boolean[] primeCheck = new boolean[1000001];
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader((System.in)));
		eratos(1000000);
		while(true) {
			int num = Integer.parseInt(br.readLine());
			if(num == 0) break;
			find(num);
		}
		br.close();
	}
	
	private static void eratos(int num) {
		//수의 범위 만큼 초기화
		primeCheck[0] = false;
		primeCheck[1] = false;
		for(int i = 2; i <= num; i++) {
			primeCheck[i] = true;
		}
		
		//범위의 제곱근 이하의 소수를 찾는다.
		for(int i = 2; i*i <= num; i++) {
			if(primeCheck[i]) {
				//배수 제거
				for(int j = i*2; j <= num; j+=i) {
					primeCheck[j] = false;
				}
			}
		}
	}
	
	private static void find(int num) {
		//순차 탐색을 하며 홀수 소수를 찾는 경우 해당 숫자를 만들어주는 쌍의 숫자 또한 홀수 소수인지 판단한다.
		//해당 숫자를 만들어주는 홀수 소수 쌍을 찾는 즉시 탐색을 종료한다. (가장 차이가 큰 쌍을 찾는 문제이기 때문에)
		for(int i = 2; i <= num/2; ++i) {
			if(i%2==0) continue;
			
			if(primeCheck[i] && (num-i)%2 != 0 && primeCheck[num-i]) {
				System.out.println(String.format("%d = %d + %d", num, i, num-i));
				return;
			}
		}
		
		//찾지 못한 경우
		System.out.println("Goldbach's conjecture is wrong.");
	}
}
