public class ArrayDeque<T> {
    private int arraySize = 8;
    private int size = 0;
    private T[] arr;

    private static final double USAGE_RATIO = 0.25;

    public ArrayDeque() {
        this.arr = (T[]) new Object[this.arraySize];
    }

    public int size() {
        return this.size;
    }

    public void addFirst(T item) {
        if (this.size >= 1) {
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
        this.size += 1;
    }

    public void addLast(T item) {
        if (needResize()) {
            increaseArraySize();
            T[] newArray = (T[]) new Object[this.arraySize];
            System.arraycopy(this.arr, 0, newArray, 0, this.size);
            this.arr = newArray;
        }
        this.arr[this.size] = item;
        this.size += 1;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void printDeque() {
        for (int i = 0; i < this.size; i++) {
            System.out.print(arr[i]);
            System.out.println(" ");
        }
    }

    public T removeFirst() {
        if (this.size == 0) {
            return null;
        }
        if (needReduceSize()) {
            reduceArraySize();
        }
        T[] newArray = (T[]) new Object[this.arraySize];
        T firstItem = this.arr[0];
        this.size -= 1;
        System.arraycopy(this.arr, 1, newArray, 0, this.size);
        this.arr = newArray;
        return firstItem;
    }

    public T removeLast() {
        if (this.size == 0) {
            return null;
        }
        T lastItem = this.arr[this.size - 1];
        this.arr[this.size - 1] = null;
        this.size -= 1;
        if (needReduceSize()) {
            reduceArraySize();
            T[] newArray = (T[]) new Object[this.arraySize];
            System.arraycopy(this.arr, 0, newArray, 0, this.size);
            this.arr = newArray;
        }
        return lastItem;
    }

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
        return this.arraySize >= 16 && (double) (this.size - 1) / this.arraySize < USAGE_RATIO;
    }

    private void increaseArraySize() {
        if (this.arraySize < 16) {
            this.arraySize = this.arraySize * 2;
        } else {
            this.arraySize = (int) Math.round(this.arraySize / USAGE_RATIO);
        }
    }

    private void reduceArraySize() {
        this.arraySize = (int) Math.round(this.size / (1.0 - USAGE_RATIO));
    }
}
