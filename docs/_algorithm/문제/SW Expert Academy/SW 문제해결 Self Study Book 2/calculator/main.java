import java.util.Scanner;

/*test data
3+(4+5)*6+7
64

(9+(5*2+1)+(3*3*7*6*9*1*7+1+8*6+6*1*1*5*2)*4*7+4*3*8*2*6+(7*8*4*5)+3+7+(2+6+5+1+7+6+7*3*(6+2)+6+6)*2+4+2*2+4*9*3)
672676

(4+8+4*(8*5*(7*(6*8)+3+(6+(3+7+1*7*5*4)*3)*2*3+5)+6+7*7)*4+2+9*4+7+2*3*(7*6*1*8)+9+9)
1974171
 */
public class main {	
	public static void main(String[] args) {
		char input[] = null;
		
		Scanner scanner = new Scanner(System.in);
		input = scanner.nextLine().toCharArray();
		scanner.close();
		
		System.out.println(calculateByPostfix(input));
	}
	
	private static int calculateByPostfix(char input[]) {
		int result = 0;
		
		char postfix[] = toPostfix(input);
		int num_st[] = new int[input.length];
		int stackCnt = 0;
		
		for(char tok : postfix) {
			//System.out.print(tok + " ");
			if(tok >= '0' && tok <= '9') {
				num_st[stackCnt++] = tok - '0';
			}
			else {
				if(stackCnt < 2) {
					continue;
				}
				
				int num1 = num_st[stackCnt - 1];
				int num2 = num_st[stackCnt - 2];
				stackCnt -= 2;
				
				int new_num = 0;
				if(tok == '*') {
					new_num = num1 * num2;
				}
				else if(tok == '+') {
					new_num = num1 + num2;
				}
				
				num_st[stackCnt++] = new_num;
			}
		}
		
		result = num_st[stackCnt - 1];
		
		return result;
	}

	//숫자는 그대로 출력
	//연산자는 스택에 넣는다.
	//')'를 만나면 '('가 나올때까지 pop, 출력
	//'('는 무조건 스택에 넣는다.
	//연산자를 스택에 넣을 때 스택 top에 우선순위가 더 큰 연산자가 있으면 모두 pop, 출력(단, 괄호 전까지)
	private static char[] toPostfix(char input[]) {
		char postfix[] = new char[input.length];
		char st[] = new char[input.length];
		
		int i = 0;
		int stackCnt = 0;
		for(char tok : input) {
			if(tok >= '0' && tok <= '9') {
				postfix[i++] = tok;
			}
			else {
				if(tok == '+' || tok == '*') {
					if(stackCnt > 0) {
						//스택에 우선 순위가 더 큰 연산자는 모두 pop, 출력(단, 괄호 전까지)
						char top = st[stackCnt - 1];
						while(getOperationOrder(tok) < getOperationOrder(top) ) {
							if(top == '(') {
								break;
							}
							
							st[stackCnt - 1] = 0;
							stackCnt--;
							
							postfix[i++] = top;
							
							top = st[stackCnt - 1];
						}
					}
					
					st[stackCnt++] = tok;
				}
				else if(tok == '(') {
					st[stackCnt++] = tok;
				}
				else if(tok == ')') {
					
					if(stackCnt > 0) {
						//'(' 를 만날때 까지 pop, 출력
						char top = st[stackCnt - 1];
						while(true) {
							st[stackCnt - 1] = 0;
							stackCnt--;
							
							if(top == '(') {
								break;
							}
							else {
								postfix[i++] = top;
								
								top = st[stackCnt - 1];	
							}
						}
					}
					
				}
			}
		}
		
		if(stackCnt > 0) {
			while(stackCnt > 0) {
				postfix[i++] = st[stackCnt - 1];
				stackCnt--;
			}
		}
		
		return postfix;
	}
	
	private static int getOperationOrder(char op) {
		if(op == '+')
			return 1;
		else if(op == '*')
			return 2;
		else if(op == '(')
			return 3;
		else
			return 0;
	}
}
