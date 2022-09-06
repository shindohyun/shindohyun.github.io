import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;
import java.util.Stack;

public class Main {
	static int N;
	static char[] s;
	static Stack<Character> stack = new Stack<Character>();
	static Stack<Long> numStack = new Stack<Long>();
	static long max = -99999999;
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt();
		s = sc.next().toCharArray();
		sc.close();
		
		
		if(N == 1)
			max = s[0] - '0';
		else 
			solution();	
		System.out.println(max);
	}
	
	private static void solution() {		
		int gMaxCnt = (N/2 + 1)/2; //괄호 최대 개수
		addG(gMaxCnt, 0, s);	
	}
	
	//괄호를 최대 개수 만큼 추가
	private static void addG(int gMaxCnt, int start, char[] str) {
		if(gMaxCnt <= 0)
			return;
		
		for(int open = start; open < str.length - 2; open++) {
			//괄호는 숫자 앞에서 열린다.
			if(str[open] >= '0' && str[open] <= '9' ) {
				int close = open + 2;
				if(str[close] >= '0' && str[close] <= '9' ) {
					char[] newStr = addGEx(open, close, str);	
					addG(gMaxCnt - 1, close + 2, newStr);
				}
				/* 괄호 안에 여러개의 연산자를 포함할 수 있는 경우 사용할 수 있는 반복문
				for(int close = open + 2; close < str.length; close++) {
					//괄호는 숫자 뒤에서 닫힌다.
					if(str[close] >= '0' && str[close] <= '9' ) {
						char[] newStr = addGEx(open, close, str);	
						addG(gMaxCnt - 1, close + 2, newStr);
					}
				}
				*/
			}
		}
	}
	
	private static char[] addGEx(int open, int close, char[] str) {
		char[] newStr = new char[str.length + 2];
		
		int j = 0;
		for(int i = 0; i < str.length; i++) {
			if(i == open) {
				if(j == 0) {
					newStr[0] = '(';
					newStr[1] = str[i];
					j = 2;
				}else {
					newStr[j++] = '(';
					newStr[j++] = str[i];
				}
			}
			else if(i == close) {
				newStr[j++] = str[i];
				newStr[j++] = ')';
			}
			else {
				newStr[j++] = str[i];
			}
		}
		//System.out.print(newStr);
		pushIntoStack(newStr);
		long result = calculate();
		//System.out.print(" " + result);
		if(max < result) {
			max = result;
		}
		//System.out.println();
		return newStr;
	}
	
	private static long calculate() {			
		char tok = 0;
		char op = 0;
				
		while(stack.size() != 0) {
			tok = stack.pop();
			if(tok == '(') {
				calculateUntilClose();
				if(op == 0)
					continue;
			}
			
			if(op == 0 && tok == '+' || tok == '-' || tok == '*')
				op = tok;
			else if(tok >= '0' && tok <= '9')
				numStack.push((long) Character.getNumericValue(tok));
			
			if(numStack.size() == 2) {
				numStack.push(opCalculate(op, numStack.pop(), numStack.pop()));
				op = 0;
			}
		}
		
		return numStack.pop();
	}
	
	private static void calculateUntilClose() {
		char tok = 0;
		Deque<Character> dq = new ArrayDeque<Character>();
		
		while(true) {
			tok = stack.pop();
			if(tok == ')')
				break;
			
			dq.add(tok);
		}
		
		Stack<Long> tempNumStack = new Stack<Long>();
		char op = 0;
		while(dq.size() != 0) {
			tok = dq.getFirst();
			dq.removeFirst();
			
			if(tok == '+' || tok == '-' || tok == '*')
				op = tok;
			else
				tempNumStack.push((long) Character.getNumericValue(tok));
			
			if(tempNumStack.size() == 2) {
				tempNumStack.push(opCalculate(op, tempNumStack.pop(), tempNumStack.pop()));
			}
		}
		
		numStack.push(tempNumStack.pop());
	}
	
	private static long opCalculate(char op, long num2, long num1) {
		switch(op) {
		case '+':
			return num1 + num2;
		case '-':
			return num1 - num2;
		case '*':
			return num1 * num2;
		default:
			return 0;
		}
	}
	
	private static void pushIntoStack(char[] str) {
		stack.clear();
		for(int i = str.length - 1; i >= 0; --i) {
			stack.push(str[i]);
		}
	}
}
