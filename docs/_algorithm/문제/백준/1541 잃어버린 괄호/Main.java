import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
	static String exp;
	static int res;
	
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		exp = br.readLine();
		br.close();
		
		solution();
		System.out.println(res);
	}
	
	private static void solution() {
		boolean isFirst = true;
		
		String[] toks = exp.split("-");
		for(int i=0; i<toks.length; i++) {
			String[] nums= toks[i].split("\\+");
			int sum = 0;
			for(int j=0; j<nums.length; j++) {
				sum += Integer.parseInt(nums[j]);
			}
			
			if(isFirst) {
				res = sum;
				isFirst = false;
			}
			else {
				res -= sum;
			}
		}
	}
}
