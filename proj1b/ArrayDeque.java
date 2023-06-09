public class ArrayDeque<T> implements Deque<T> {
    private int size = 0;
    private T[] arr;
    private int first;
    private int last;

    private static final double USAGE_RATIO = 0.25;

    public ArrayDeque() {
        this.arr = (T[]) new Object[8];
    }

    public int size() {
        return this.size;
    }

    private void resize(int newSize) {
        T[] newArray = (T[]) new Object[newSize];
        // handle resize when array is full
        if (this.size == this.arr.length) {
            System.arraycopy(this.arr, this.first, newArray, 0, this.size - this.first);
            if (this.first != 0) {
                System.arraycopy(this.arr, 0, newArray, this.size - this.first, this.first);
            }

        } else {
            // handle array length > 16 & usage ratio < 25%;
            if (this.first < this.last) {
                System.arraycopy(this.arr, this.first, newArray, 0, this.size);
            } else {
                System.arraycopy(this.arr, this.first, newArray, 0, this.arr.length);
                System.arraycopy(this.arr, 0, newArray, this.arr.length, this.last + 1);
            }
        }
        this.arr = newArray;
        this.first = 0;
        this.last = this.size - 1;
    }

    private boolean shouldReduceArraySize() {
        return this.arr.length > 16 && ((double) this.size / this.arr.length) < USAGE_RATIO;
    }

    private void recalculateFirst() {
        int nextFirst = this.first - 1;
        this.first = nextFirst < 0 ? this.arr.length - 1 : nextFirst;
    }

    private void recalculateLast() {
        int nextLast = this.last + 1;
        this.last = nextLast == this.arr.length ? 0 : nextLast;
    }

    @Override
    public void addFirst(T item) {
        if (this.size == this.arr.length) {
            resize(this.arr.length * 2);
        }
        if (this.size >= 1) {
            recalculateFirst();
        }
        this.arr[first] = item;
        this.size += 1;

    }

    @Override
    public void addLast(T item) {
        if (this.size == this.arr.length) {
            resize(this.arr.length * 2);
        }
        if (this.size >= 1) {
            recalculateLast();
        }
        this.arr[last] = item;
        this.size += 1;

    }

    @Override
    public void printDeque() {
        if (isEmpty()) {
            return;
        }
        if (this.first < this.last) {
            for (int i = this.first; i <= this.last; i++) {
                System.out.print(this.arr[i]);
                System.out.print(" ");
            }
        } else {
            for (int i = this.first; i < this.arr.length; i++) {
                System.out.print(this.arr[i]);
                System.out.print(" ");
            }
            for (int i = 0; i <= this.last; i++) {
                System.out.print(this.arr[i]);
                System.out.print(" ");
            }
        }
        System.out.println();
    }

    @Override
    public T removeFirst() {
        if (this.size == 0) {
            return null;
        }
        T item = this.arr[this.first];
        this.arr[this.first] = null;
        this.size -= 1;
        // not only one item in which situation two pointers are the same
        if (this.first != this.last) {
            this.first = this.first + 1 == this.arr.length ? 0 : this.first + 1;
        }
        if (shouldReduceArraySize()) {
            resize((int) Math.round(this.size / (1 - USAGE_RATIO)));
        }
        return item;
    }

    @Override
    public T removeLast() {
        if (this.size == 0) {
            return null;
        }
        T item = this.arr[this.last];
        this.size -= 1;
        if (this.first != this.last) {
            this.last = this.last - 1 < 0 ? this.arr.length - 1 : this.last - 1;
        }
        if (shouldReduceArraySize()) {
            resize((int) Math.round(this.size / (1 - USAGE_RATIO)));
        }
        return item;
    }

    @Override
    public T get(int index) {
        if (index >= this.arr.length) {
            return null;
        }
        int actualIndex = this.first + index;
        if (actualIndex >= this.arr.length) {
            actualIndex = actualIndex - this.arr.length;
        }
        return this.arr[actualIndex];
    }
}
