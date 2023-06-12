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


public class ArrayDeque<T> implements Deque<T> {
    private int arraySize = 8;
    private int size = 0;
    private T[] arr;

    static final double USAGE_RATIO = 0.25;
    static final double LOW_USAGE_RATIO = 0.05;

    public ArrayDeque() {
        this.arr = (T[]) new Object[this.arraySize];
    }

    public ArrayDeque(ArrayDeque<T> other) {
        this.arr = copyArray(other);
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public void addFirst(T item) {
        if(this.size > 1) {
            if (needResize()) {
                increaseArraySize();
            }
            T[] newArray = (T[]) new Object[this.arraySize];
            newArray[0] = item;
            System.arraycopy(this.arr, 0, newArray, 1, this.size);
            this.arr = newArray;
        } else {
            this.arr[0] = item;
        }
        this.size+=1;
    }

    @Override
    public void addLast(T item) {
        if (needResize()) {
            increaseArraySize();
            T[] newArray = (T[]) new Object[this.arraySize];
            System.arraycopy(this.arr, 0, newArray, 0, this.size);
            this.arr = newArray;
        }
        this.arr[this.size] = item;
        this.size+=1;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void printDeque() {
        for(int i = 0; i < this.size; i++) {
            System.out.println(arr[i]);
        }
    }

    @Override
    public T removeFirst() {
        if(needReduceSize()) {
            reduceArraySize();
        }
        T[] newArray = (T[]) new Object[this.arraySize];
        T firstItem = this.arr[0];
        this.size -= 1;
        System.arraycopy(this.arr, 1, newArray, 0, this.size);
        this.arr = newArray;
        return firstItem;
    }

    @Override
    public T removeLast() {
        T lastItem = this.arr[this.size - 1];
        this.arr[this.size - 1] = null;
        this.size -= 1;
        if(needReduceSize()) {
            reduceArraySize();
            T[] newArray = (T[]) new Object[this.arraySize];
            System.arraycopy(this.arr, 0, newArray, 0, this.size);
            this.arr = newArray;
        }
        return lastItem;
    }

    @Override
    public T get(int index) {
        return this.arr[index];
    }

    private static <T> T[] copyArray(ArrayDeque<T> other) {
        T[] copyArr = (T[]) new Object[other.size()];
        System.arraycopy(other.arr, 0, copyArr, 0, other.size());
        return copyArr;
    }

    private boolean needResize() {
        return this.size == this.arraySize;
    }

    private boolean needReduceSize() {
        double ratio = this.size >= 16 ? USAGE_RATIO : LOW_USAGE_RATIO;
        return ((double) (this.size - 1) / this.arraySize) < ratio;
    }

    private void increaseArraySize() {
        if(this.arraySize < 16) {
            this.arraySize = this.arraySize * 2;
        } else {
            this.arraySize = (int) Math.round(this.arraySize / USAGE_RATIO);
        }
    }

    private void reduceArraySize() {
        double ratio = this.size >= 16 ? USAGE_RATIO : LOW_USAGE_RATIO;
        this.arraySize = (int) Math.round(this.size / (1.0 - ratio));
    }
}
