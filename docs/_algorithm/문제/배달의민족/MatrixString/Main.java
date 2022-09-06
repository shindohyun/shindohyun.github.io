import java.util.Scanner;

//입력
//N : column 개수
//U : Upper 0 row 숫자 합
//L : Lower 1 row 숫자 합
//C : column 숫자 합

/* TEST DATA
5 3 2
2 1 1 0 1

5 2 3 
0 0 1 1 2

4
2 2
2 0 2 0
 */
public class Main {

	public static void main(String[] args) {
		int N, U, L;
		int C[];
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt();
		U = sc.nextInt();
		L = sc.nextInt();
		C = new int[N];
		for(int i = 0; i < N; i++) {
			C[i] = sc.nextInt();
		}
		sc.close();
		
		System.out.println(solution(U, L, C));
	}
	
	private static String solution(int U, int L, int C[]) {
		String result = "";
		final String impossible = "IMPOSSIBLE";
		
		//M: Matrix 여러개 나올 수 있는데 그 중 하나만 리턴하면 된다.
		//Matrix 성립 조건 : C의 모든 값의 합은 U + L과 같아야한다.
		//만들 수 없는 Matrix인 경우 'IMPOSSIBLE' 반환
		//Upper, Lower에 바로바로 수를 추가해주기 때문에 Matrix 의 이중배열을 생성할 필요가 없다.
		int Csum = 0;
		int tempU = U;
		int tempL = L;
		String r0 = "";
		String r1 = "";
		
		for(int i = 0; i < C.length; i++) {
			Csum += C[i];

			if(C[i] == 2) {
				tempU--;
				tempL--;
				r0 += "1";
				r1 += "1";
			}
			else if(C[i] == 1) {
				//Upper에 우선적으로 할당
				if(tempU > 0) {
					tempU--;
					r0 += "1";
					r1 += "0";
				}
				else {
					tempL--;
					r0 += "0";
					r1 += "1";
				}
			}
			else {
				r0 += "0";
				r1 += "0";
			}
		}
		
		if(Csum != U + L) {
			return impossible;
		}
		
		result = r0 + "," + r1;
		return result;
	}
}

