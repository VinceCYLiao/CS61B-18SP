public class LinkedListDeque<T> {
    class Node {
        Node prev;
        Node next;
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


    private final Node sentinel;
    private int size;

    public LinkedListDeque() {
        this.sentinel = new Node();
    }

    public void addFirst(T item) {
        Node originalNextPointer = this.sentinel.next;
        Node newNode = new Node(item);
        this.sentinel.next = newNode;
        newNode.prev = this.sentinel;
        if (originalNextPointer == null) {
            this.sentinel.prev = newNode;
            newNode.next = this.sentinel;
        } else {
            newNode.next = originalNextPointer;
            originalNextPointer.prev = newNode;
        }
        this.size++;
    }

    public void addLast(T item) {
        Node newNode = new Node(item);
        Node originalLastNode = this.sentinel.prev;
        this.sentinel.prev = newNode;
        newNode.next = this.sentinel;
        if (originalLastNode == null) {
            this.sentinel.next = newNode;
            newNode.prev = this.sentinel;
        } else {
            originalLastNode.next = newNode;
            newNode.prev = originalLastNode;
        }
        this.size++;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public int size() {
        return this.size;
    }

    public void printDeque() {
        Node node = this.sentinel.next;
        while (node != null && node != this.sentinel) {
            System.out.print(node.value);
            System.out.print(" ");
            node = node.next;
        }
        System.out.println();
    }

    public T removeFirst() {
        Node firstNode = this.sentinel.next;
        if (firstNode == null) {
            return null;
        }
        if (this.size == 1) {
            this.sentinel.next = null;
            this.sentinel.prev = null;
        } else {
            this.sentinel.next = firstNode.next;
            firstNode.next.prev = this.sentinel;
        }
        this.size--;
        return firstNode.value;
    }

    public T removeLast() {
        Node originalLastNode = this.sentinel.prev;
        if (originalLastNode == null) {
            return null;
        }
        if (this.size == 1) {
            this.sentinel.prev = null;
            this.sentinel.next = null;
        } else {
            this.sentinel.prev = originalLastNode.prev;
            originalLastNode.prev.next = this.sentinel;
        }
        this.size--;
        return originalLastNode.value;
    }

    public T get(int index) {
        if (index >= this.size) {
            return null;
        }
        Node targetNode;
        do {
            targetNode = this.sentinel.next;
            index--;
        } while (index != -1);
        return targetNode.value;
    }

    public T getRecursive(int index) {
        if (index >= this.size) {
            return null;
        }
        return recursivelyFindTargetNodeValue(index, this.sentinel.next);
    }

    private T recursivelyFindTargetNodeValue(int index, Node node) {
        if (index == 0) {
            return node.value;
        } else {
            return recursivelyFindTargetNodeValue(index - 1, node.next);
        }
    }
}
