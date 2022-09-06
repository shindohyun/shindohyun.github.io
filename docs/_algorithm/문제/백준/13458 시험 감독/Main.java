import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

	/*
	 * 목적: 각 시험장의 응시생을 모두 감시하기 위한 최소 감독관 수
	 * 
	 * 조건:
	 * 1. 시험장
	 * - 개수: N (1~1,000,000)
	 * - 각 시험장 응시자 수 Ai (1~1,000,000)
	 * 2. 감독관
	 * - 총 감독관: 각 시험장에서 B명 감시, 무조건 1명
	 * - 부 감독관: 각 시험장에서 C명 감시, 여러명
	 * - B, C: 1~1,000,000
	 * 
	 * 알고리즘:
	 * 1. 각 시험장 탐색
	 * 2. 필요한 감독관 수 누적
	 * - B를 뺀 응시자 수 / C, 나머지 존재하는 경우 + 1
	 */
	static long N;
	static long[] places = new long[1000000]; // 각 시험장의 응시자 수
	static long B, C;
	static long res;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		N = Long.parseLong(br.readLine());
		st = new StringTokenizer(br.readLine());
		for(int i = 0; i < N; i++) {
			places[i] = Long.parseLong(st.nextToken());
		}
		st = new StringTokenizer(br.readLine());
		B = Long.parseLong(st.nextToken());
		C = Long.parseLong(st.nextToken());
		res = 0;
		br.close();
		
		solution();
		System.out.println(res);
	}
	
	public static void solution() {		
		long num;
		long cnt;
		long rest;
		
		for(int i = 0; i < N; i++) {
			num = places[i];
			cnt = 0;
			
			num -= B;
			cnt++;
			
			if(num > 0) {
				cnt += num / C;
				rest = num % C;
				if(rest > 0) cnt++;
			}
			
			res += cnt;
		}
	}
}
