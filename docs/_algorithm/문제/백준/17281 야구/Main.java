import java.util.Scanner;

public class Main {
	
	static int N; //2~50
	static int scores[][]; //1~9까지의 선수가 각 이닝에서 얻는 결과
	static int order[] = new int[10];
	static boolean visit[] = new boolean[10];
	static int max = 0;
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt();
		scores = new int[N][10];
		for(int n = 0; n < N; n++) {
			for(int num = 1; num <= 9; num++) {
				scores[n][num] = sc.nextInt();
			}
		}
		sc.close();
		
		solution();
		System.out.println(max);
	}

	private static void solution() {
		order[4] = 1;
		visit[1] = true;
		rec(1);
	}
	
	private static void printOrder() {
		for(int i = 1; i < 10; i++) {
			System.out.print(order[i] + " ");
		}
		System.out.println();
	}
	
	private static void rec(int orderNum) {
		if(orderNum == 10) {
			//타순이 모두 정해졌을 때 경기 시작
			play();
			return;
		}
		
		if(orderNum == 4) {
			rec(orderNum+1);
		}
		else {
			for(int i = 2; i < 10; i++) {
				if(visit[i] == true) continue;
				
				visit[i] = true;
				order[orderNum] = i;
				
				rec(orderNum+1);
				
				order[orderNum] = 0;
				visit[i] = false;
			}
		}
	}
	
	private static void play() {
		//printOrder();
		
		int score = 0;
		int pos[]; 
		int outCnt;
		int orderNum = 1;
		
		for(int i = 0; i < N; i++) {
			pos = new int[3];
			outCnt = 0;
			
			while(outCnt < 3) {
				if(orderNum > 9)
					orderNum = 1;

				int getScore = scores[i][order[orderNum++]]; //i 이닝에서 각 순서의 선수가 얻는 점수
				
				if(getScore == 0) {
					outCnt++;
				}
				else if(getScore == 4) {
					score++;
					for(int p = 0; p < 3; p++) {
						if(pos[p] != 0) {
							pos[p] = 0;
							score++;
						}
					}
				}
				else {
					//더해주고 점수 내기
					for(int p = 0; p < 3; p++) {
						if(pos[p] != 0) {
							pos[p] += getScore;
							if(pos[p] >= 4) {
								pos[p] = 0;
								score++;
							}
						}
					}
					//빈 자리에 점수 넣기
					for(int p = 0; p < 3; p++) {
						if(pos[p] == 0) {
							pos[p] = getScore;
							break;
						}
					}
				}
				
				//System.out.print(i+1 + "회 " + (orderNum-1) + "번째 공격 ");
				//System.out.println(pos[0] + "," + pos[1] + "," + pos[2] + " 누적점수: " + score + " 아웃: " + outCnt);
			}
		}
		
		if(max < score)
			max = score;
	}
}
