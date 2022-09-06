import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	static int t, sm, sc, si;
	static int pt;
	static int inputPt;
	static int[] arr = new int[100001];
	static String code;
	static String input;
	static ArrayList<Bracket> bracketList = new ArrayList<Bracket>();
	static String results[] = new String[20];
	static int lastIndex = 0; //루프에 걸렸을때 마지막 루프의 닫힘 괄호 인덱스
	
	static class Bracket{
		int open, close;
		Bracket(int open, int close){
			this.open = open;
			this.close = close;
		}
	}
	
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		t = scanner.nextInt();

		for(int i = 0; i < t; i++) {
			sm = scanner.nextInt();
			sc = scanner.nextInt();
			si = scanner.nextInt();
			
			pt = 0;
			inputPt = 0;
			lastIndex = 0;
			
			for(int j = 0; j < sm; j++) arr[i] = 0;
			
			code = scanner.next();
			input = scanner.next();
			
			makeBracket();
			
			results[i] = solution(); 
		}
		
		scanner.close();
		
		for(int i = 0; i < t; i++) {
			System.out.println(results[i]);
		}
	}

	private static String solution() {				
		int codePt = 0;
		int cmdCnt = 0;
		
		boolean isSuccess = false;
		while(cmdCnt++ < 50000000) {
			codePt = cmdEx(codePt, code.charAt(codePt));
			
			if(codePt == sc) {
				isSuccess = true;
				break;
			}
		}
		
		if(isSuccess) {
			return "Terminates";
		}
		else {
			Bracket lastCloseBracket = bracketList.get(0);
			for(Bracket bracket: bracketList) {
				if(bracket.close == lastIndex)
				{
					lastCloseBracket = bracket;
					break;
				}
			}

			return "Loops " + Integer.toString(lastCloseBracket.open) + " " + Integer.toString(lastCloseBracket.close);
		}
	}
	
	private static int cmdEx(int codePt, char cmd) {
		switch(cmd) {
		case '-':
			arr[pt] = (arr[pt] - 1) % 256; 
			return codePt+1;
		case '+':
			arr[pt] = (arr[pt] + 1) % 256; 
			return codePt+1;
		case '<':
			pt--;
			if(pt < 0) {
				pt = sm-1;
			}
			return codePt+1;
		case '>':
			pt++;
			if(pt >= sm) {
				pt = 0;
			}
			return codePt+1;
		case '[':
			if(arr[pt] == 0) {
				//]로 점프
				Bracket openBracket = null;
				for(Bracket bracket: bracketList) {
					if(bracket.open == codePt) {
						openBracket = bracket;
						break;
					}
				}
				return openBracket.close;
			}
			return codePt+1;
		case ']':
			if(lastIndex < codePt)
				lastIndex = codePt;
			
			if(arr[pt] != 0) {
				//[로 점프
				Bracket closeBracket = null;
				for(Bracket bracket: bracketList) {
					if(bracket.close == codePt) {
						closeBracket = bracket;
						break;
					}
				}
				
				return closeBracket.open;
			}
			return codePt+1;
		case '.':
			//포인터 위치의 수 출력
			return codePt+1;
		case ',':
			if(inputPt >= si) {
				arr[pt] = 255;	
			}
			else if(inputPt < si) {
				arr[pt] = (int)input.charAt(inputPt++); 
			}
			return codePt+1;
		}
		
		return 0;
	}
	
	private static void makeBracket() {
		bracketList.clear();
		
		for(int i = 0; i < sc; i++) {
			if(code.charAt(i) == '[') {
				bracketList.add(new Bracket(i, findClosePt(i)));
			}
		}
	}
	
	private static int findClosePt(int openPt) {
		int openCnt = 0;
		
		for(int i = openPt+1; i < sc; i++) {
			if(code.charAt(i) == '[') {
				openCnt++; //중간이 열림 괄호를 만나면 열림 괄호 개수를 추가한다.
			}
			else if(code.charAt(i) == ']') {
				openCnt--; //닫힘 괄호를 만났을때 열림 괄호 개수를 감소시킨다.
			}
			
			if(openCnt < 0) {
				return i; //열림 괄호 개수가 음수가 된 것은 짝에 맞는 닫힘 괄호의 위치를 찾은 것.
			}
		}
		
		return -1; //프로그램이 잘 짜여 있음을 보장하기 때문에 못찾는 경우는 없어야한다.
	}
}
