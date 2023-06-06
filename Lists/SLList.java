public class SLList {
    public IntNode first;

    public SLList(int x) {
        this.first = new IntNode(x, null);
    }

    public static void main(String[] args) {

    }

    public void addFirst(int x) {
        this.first = new IntNode(x, this.first);
    }

    public int getFirst() {
        return this.first.item;
    }
}
