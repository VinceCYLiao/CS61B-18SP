interface Deque <T>{
    void addFirst(T item); // Adds an item of type T to the front of the deque.
    void addLast(T item); // Adds an item of type T to the back of the deque.
    boolean isEmpty(); // Returns true if deque is empty, false otherwise.
    int size(); //Returns the number of items in the deque.
    void printDeque(); //Prints the items in the deque from first to last, separated by a space. Once all the items have been printed, print out a new line.
    T removeFirst(); // Removes and returns the item at the front of the deque. If no such item exists, returns null.
    T removeLast(); // Removes and returns the item at the back of the deque. If no such item exists, returns null.
    T get(int index); // Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth. If no such item exists, returns null. Must not alter the deque!
}

class Node<T> {
    Node<T> prev;
    Node<T> next;
    T value;
    public Node(T value) {
        this.value = value;
        this.next = null;
        this.prev = null;
    }

    public Node() {
        this.value = null;
        this.next = null;
        this.prev = null;
    }
}

public class LinkedListDeque<T> implements Deque<T> {
    private final Node<T> sentinel;
    private Node<T> lastNode;
    private int size;

    public LinkedListDeque() {
        this.sentinel = new Node<>();
        this.lastNode = this.sentinel;
    }

    @Override
    public void addFirst(T item) {
        Node<T> originalNextPointer = this.sentinel.next;
        Node<T> newNode =  new Node<>(item);
        if(isEmpty()) {
            this.lastNode = newNode;
        }
        newNode.prev = this.sentinel;
        this.sentinel.next = newNode;
        newNode.next = originalNextPointer;
        originalNextPointer.prev = newNode;
        this.size++;
    }

    @Override
    public void addLast(T item) {
       Node<T> newNode = new Node<>(item);
       newNode.prev = this.lastNode;
       this.lastNode.next = newNode;
       this.lastNode = newNode;
       this.size++;
    }

    @Override
    public boolean isEmpty() {
        return this.sentinel.next == null;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public void printDeque() {
        Node<T> node = this.sentinel.next;
        while (node != null){
            System.out.print(node.value);
            System.out.print(" ");
            node = node.next;
        }
        System.out.println();
    }

    @Override
    public T removeFirst() {
        Node<T> firstNode = this.sentinel.next;
        if(firstNode == null) {
            return null;
        }
        this.sentinel.next = firstNode.next;
        if(this.sentinel.next != null) {
            this.sentinel.next.prev = this.sentinel;
        } else {
            this.lastNode = this.sentinel;
        }
        this.size--;
        return firstNode.value;
    }

    @Override
    public T removeLast() {
        if(this.lastNode == this.sentinel) {
            return null;
        }
        Node<T> originalLastNode = this.lastNode;
        this.lastNode.prev.next = null;
        this.lastNode = this.lastNode.prev;
        this.size--;
        return originalLastNode.value;
    }

    @Override
    public T get(int index) {
        if(index >= size) {
            return null;
        }
        Node<T> targetNode;
        do {
            targetNode = this.sentinel.next;
            index--;
        }while(index != -1);
        return targetNode.value;
    }
}