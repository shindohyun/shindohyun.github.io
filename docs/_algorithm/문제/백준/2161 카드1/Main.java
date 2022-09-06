import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

public class Main {
	static int N; // 1~1000
	
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		Queue<Integer> cards = new LinkedList<Integer>();
		for(int i = 0; i < N; i++) {
			cards.add(i+1);
		}
		while(cards.size() > 1) {
			Integer discard = cards.poll();
			Integer back = cards.poll();
			cards.add(back);
			System.out.print(discard + " ");
		}
		System.out.println(cards.poll());
		br.close();
	}

}
