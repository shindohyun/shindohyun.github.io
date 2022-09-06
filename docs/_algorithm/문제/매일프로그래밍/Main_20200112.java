public class Main_20200112 {
	static class Node{
		int v;
		Node next;
		
		public Node(int v) {
			this.v = v;
		}
	}
	
	static class SingleLinkedList{
		Node head;
		Node tail;
		
		public SingleLinkedList() {}
		
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
	}
	
	public static void main(String[] args) {
		SingleLinkedList list = new SingleLinkedList();
		list.add(new Node(1));
		list.add(new Node(2));
		list.add(new Node(3));
		list.add(new Node(4));
		list.add(new Node(5));
		
		System.out.println(findCenter(list));
	}
	
	public static int findCenter(SingleLinkedList list) {
		Node fast = list.head;
		Node slow = list.head;
		
		while(fast.next != null && fast.next.next != null) {						
			fast = fast.next.next;
			slow = slow.next;
		}
		
		return slow.v;
	}
}
