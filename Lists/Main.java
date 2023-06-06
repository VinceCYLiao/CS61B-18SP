public class Main {
    public static void main(String[] args) {
        IntNode n = new IntNode(10, null);
        n.addFirst(5);
        n.addFirst(1);
        System.out.println(n.item);
        System.out.println(n.next.item);
        System.out.println(n.next.next.item);

    }
}

class IntNode {
    public int item;
    public IntNode next;

    public IntNode(int i, IntNode n) {
        this.item = i;
        this.next = n;
    }

    public void addFirst(int x) {
        this.next = new IntNode(this.item, this.next);
        this.item = x;
    }
}