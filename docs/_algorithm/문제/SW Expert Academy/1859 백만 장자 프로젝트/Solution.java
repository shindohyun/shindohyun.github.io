/////////////////////////////////////////////////////////////////////////////////////////////
// 기본 제공코드는 임의 수정해도 관계 없습니다. 단, 입출력 포맷 주의
// 아래 표준 입출력 예제 필요시 참고하세요.
// 표준 입력 예제
// int a;
// double b;
// char g;
// String var;
// long AB;
// a = sc.nextInt();                           // int 변수 1개 입력받는 예제
// b = sc.nextDouble();                        // double 변수 1개 입력받는 예제
// g = sc.nextByte();                          // char 변수 1개 입력받는 예제
// var = sc.next();                            // 문자열 1개 입력받는 예제
// AB = sc.nextLong();                         // long 변수 1개 입력받는 예제
/////////////////////////////////////////////////////////////////////////////////////////////
// 표준 출력 예제
// int a = 0;                            
// double b = 1.0;               
// char g = 'b';
// String var = "ABCDEFG";
// long AB = 12345678901234567L;
//System.out.println(a);                       // int 변수 1개 출력하는 예제
//System.out.println(b); 		       						 // double 변수 1개 출력하는 예제
//System.out.println(g);		       						 // char 변수 1개 출력하는 예제
//System.out.println(var);		       				   // 문자열 1개 출력하는 예제
//System.out.println(AB);		       				     // long 변수 1개 출력하는 예제
/////////////////////////////////////////////////////////////////////////////////////////////
import java.util.Scanner;
import java.io.FileInputStream;

/*
   사용하는 클래스명이 Solution 이어야 하므로, 가급적 Solution.java 를 사용할 것을 권장합니다.
   이러한 상황에서도 동일하게 java Solution 명령으로 프로그램을 수행해볼 수 있습니다.
 */
class Solution
{
	static int N; //2~1,000,000
	static int m[]; //0~10,000
	
	public static void main(String args[]) throws Exception
	{
		/*
		   아래의 메소드 호출은 앞으로 표준 입력(키보드) 대신 input.txt 파일로부터 읽어오겠다는 의미의 코드입니다.
		   여러분이 작성한 코드를 테스트 할 때, 편의를 위해서 input.txt에 입력을 저장한 후,
		   이 코드를 프로그램의 처음 부분에 추가하면 이후 입력을 수행할 때 표준 입력 대신 파일로부터 입력을 받아올 수 있습니다.
		   따라서 테스트를 수행할 때에는 아래 주석을 지우고 이 메소드를 사용하셔도 좋습니다.
		   단, 채점을 위해 코드를 제출하실 때에는 반드시 이 메소드를 지우거나 주석 처리 하셔야 합니다.
		 */
		//System.setIn(new FileInputStream("res/input.txt"));

		/*
		   표준입력 System.in 으로부터 스캐너를 만들어 데이터를 읽어옵니다.
		 */
		Scanner sc = new Scanner(System.in);
		int T;
		T=sc.nextInt();
		/*
		   여러 개의 테스트 케이스가 주어지므로, 각각을 처리합니다.
		*/

		for(int test_case = 1; test_case <= T; test_case++)
		{
		
			/////////////////////////////////////////////////////////////////////////////////////////////
			/*
				 이 부분에 여러분의 알고리즘 구현이 들어갑니다.
			 */
			/////////////////////////////////////////////////////////////////////////////////////////////
			N = sc.nextInt(); 
			m= new int[N]; 
			for(int i = 0; i < N; i++) {
				m[i] = sc.nextInt();
			}
			
			System.out.println("#" + Integer.toString(test_case) + " " + solution());
		}
	}
	
	private static long solution() {
		//뒤에서 부터 매매가가 최고치를 갱신하고 최고치 보다 낮은 매매가는 최고치 매매가와의 차액(이익액)을 총 이익액에 합해준다.
		//문제에서 주인공은 매매가를 미리 예측하고 있고 매매가가 최고치인 지점에는 팔았어야함을 알고있다.
		//또한 매매가가 최고치에 이르기 전에는 물건을 사야함을 알고있다.
		//매매가 최고치와 구매시 매매가의 차액으로 이득을 남기는 것.
		//최고치 매매가 보다 1만 낮아도 이득을 보기 때문에 무조건 사야한다. 
		//앞에서 부터 탐색하면 미래에 더 높은 매매가가 있는지 알 수 없다. 예) 3이 최고치인줄알고 이익을 냈는데 뒤에 5가 있었다면 후회함
		//즉, 최고치인 지점 마다 이익을 내야하는 것. 예) 1 2 4 2 1 [9] 3 1 [5]  ([최고치 지점])
		int max = m[N-1];
		long sum = 0;
		for(int i = N - 2; i >= 0; i--) {
			if(max < m[i]) {
				max = m[i];
			}
			else {
				sum += max - m[i];
			}
		}
		return sum;
	}
}