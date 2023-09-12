package synthesizer;

import java.util.Iterator;

public class ArrayRingBuffer<T> extends AbstractBoundedQueue<T> {
    /* Index for the next dequeue or peek. */
    private int first;            // index for the next dequeue or peek
    /* Index for the next enqueue. */
    private int last;
    /* Array for storing the buffer data. */
    private T[] rb;

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        this.first = 0;
        this.last = 0;
        this.fillCount = 0;
        this.capacity = capacity;
        rb = (T[]) new Object[capacity];
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow"). Exceptions
     * covered Monday.
     */
    public void enqueue(T x) {
        if (this.isFull()) {
            throw new RuntimeException("The buffer is full");
        }
        this.rb[last] = x;
        this.last = this.last == this.capacity - 1 ? 0 : this.last + 1;
        this.fillCount++;
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow"). Exceptions
     * covered Monday.
     */
    public T dequeue() {
        if (this.isEmpty()) {
            throw new RuntimeException("Buffer is empty");
        }
        T item = this.rb[this.first];
        this.first = this.first == this.capacity - 1 ? 0 : this.first + 1;
        this.fillCount--;
        return item;
    }

    /**
     * Return oldest item, but don't remove it.
     */
    public T peek() {
        if (this.isEmpty()) {
            throw new RuntimeException("The buffer is empty.");
        }
        return this.rb[this.first];
    }

    private class ArrayRingBufferIterator implements Iterator<T> {

        private int count;
        private int index;

        public ArrayRingBufferIterator() {
            this.count = 0;
            this.index = first;
        }

        @Override
        public boolean hasNext() {
            return this.count < fillCount;
        }

        @Override
        public T next() {
            T item = rb[this.index];
            this.count++;
            index = index == capacity - 1 ? 0 : index + 1;
            return item;
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayRingBufferIterator();
    }
}
