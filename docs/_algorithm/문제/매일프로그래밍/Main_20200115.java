import java.util.Scanner;

public class Main_20200115 {
	static class Node{
		int v;
		Node next;
		
		public Node() {}
	}
	
	static class List{
		Node head;
		Node tail;
		
		public List() {}
		
		public void add(Node node) {
			if(head == null) {
				head = node;
				tail = node;
			}
			else {
				tail.next = node;
				tail = node;
			}
		}
		
		public void print() {
			Node cur = head;
			while(true) {
				System.out.print(Integer.toString(cur.v));
				if(cur.next == null)break;
				System.out.print("->");
				cur = cur.next;
			}
			System.out.println();
		}
	}
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String Atoks[] = sc.next().split("");
		String Btoks[] = sc.next().split("");
		sc.close();
		
		List A = new List();
		List B = new List();
		
		for(int i = 0; i < Atoks.length; i++) {
			Node node = new Node();
			node.v = Integer.parseInt(Atoks[i]);
			A.add(node);
		}
		for(int i = 0; i < Btoks.length; i++) {
			Integer.parseInt(Btoks[i]);
			Node node = new Node();
			node.v = Integer.parseInt(Btoks[i]);
			B.add(node);
		}
		
		solution(A, B);
	}
	
	private static void solution(List A, List B) {
		List SUM = new List();
		
		Node curA = A.head;
		Node curB = B.head;
		
		while(true) {
			int av = 0;
			int bv = 0;
			if(curA != null) {
				av = curA.v;
			}
			if(curB != null) {
				bv = curB.v;
			}
			
			int sv = av + bv;
			
			boolean up = false;
			if(sv > 9) {
				sv = sv - 10;
				up = true;
			}
			
			Node sn = new Node();
			sn.v = sv;
			SUM.add(sn);
			
			if(curA.next == null && curB.next == null) {
				if(up) {
					Node un = new Node();
					un.v = 1;
					SUM.add(un);
				}
				break;
			}
			
			if(up) {
				curA.next.v++; //A, B 둘 중 하나에
			}
			
			curA = curA.next;
			curB = curB.next;
		}
		
		SUM.print();
	}
	
}
