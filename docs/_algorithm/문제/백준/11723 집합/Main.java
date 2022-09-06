import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Main {
	static int M; //1~3,000,000
	static boolean[] S = new boolean[20];
	static char op;
	static int x; //1~20
	static boolean[] TS = new boolean[20];
	static boolean[] FS = new boolean[20];
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		for(int i = 0; i < 20; i++) {
			TS[i] = true;
			FS[i] = false;
		}
		M = Integer.parseInt(br.readLine());
		StringTokenizer st;
		for(int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			op = st.nextToken().charAt(1);
			if(op != 'l' && op != 'm') //all, empty
				x = Integer.parseInt(st.nextToken());
			solution();
		}
		br.close();
		
		bw.flush();
		bw.close();
	}
	
	private static void solution() throws IOException{
		int idx = x-1;
		
		switch(op) {
		case 'd': //add
			if(!S[idx]) S[idx] = true;
			break;
		case 'e': //remove
			if(S[idx]) S[idx] = false;
			break;
		case 'h': //check
			if(S[idx])
				bw.write("1\n");
			else 
				bw.write("0\n");
			break;
		case 'o': //toggle
			S[idx] = !S[idx];
			break;
		case 'l': //all
			S = TS;
			break;
		case 'm': //empty
			S = FS;
			break;
		}
	}
}
