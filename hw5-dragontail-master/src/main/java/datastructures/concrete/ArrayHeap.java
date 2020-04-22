package datastructures.concrete;

import java.util.Arrays;

import datastructures.interfaces.IPriorityQueue;
import misc.exceptions.EmptyContainerException;

/**
 * See IPriorityQueue for details on what each method must do.
 */
public class ArrayHeap<T extends Comparable<T>> implements IPriorityQueue<T> {
    // See spec: you must implement a implement a 4-heap.
    private static final int NUM_CHILDREN = 4;

    // You MUST use this field to store the contents of your heap.
    // You may NOT rename this field: we will be inspecting it within
    // our private tests.
    private T[] heap;
    private int totalElements;
    // Feel free to add more fields and constants.

    public ArrayHeap() {
        heap = makeArrayOfT(10);
    }

    /**
     * This method will return a new, empty array of the given size that can contain
     * elements of type T.
     *
     * Note that each element in the array will initially be null.
     */
    @SuppressWarnings("unchecked")
    private T[] makeArrayOfT(int size) {
        // This helper method is basically the same one we gave you
        // in ArrayDictionary and ChainedHashDictionary.
        //
        // As before, you do not need to understand how this method
        // works, and should not modify it in any way.
        return (T[]) (new Comparable[size]);
    }

    @Override
    public T removeMin() {
        if (totalElements == 0) {
            throw new EmptyContainerException();
        }
        T item = heap[0];
        heap[0] = heap[totalElements - 1];
        heap[totalElements - 1] = null;
        totalElements--;
        int index = 0;
        boolean sorted = false;
        while (!sorted && 4 * index + 1 < heap.length) {
            T store = heap[index];
            if (heap[4 * index + 1] != null) {
                T findMin = heap[4 * index + 1];
                int childIndex = 1;
                for (int i = 2; i <= 4; i++) {
                    if (heap[4 * index + i] == null) {
                        break;
                    }
                    if (findMin.compareTo(heap[4 * index + i]) > 0) {
                        findMin = heap[4 * index + i];
                        childIndex = i;
                    }
                }
                if ((store.compareTo(findMin)) > 0) {
                    heap[index] = heap[4 * index + childIndex];
                    heap[4 * index + childIndex] = store;
                    index = 4 * index + childIndex;
                } else {
                    sorted = true;
                }
            } else {
                sorted = true;
            }
        }
        return item;
    }

    @Override
    public T peekMin() {
        if (totalElements == 0) {
            throw new EmptyContainerException();
        }
        return heap[0];
    }

    @Override
    public void insert(T item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (totalElements == heap.length) {
            heap = Arrays.copyOf(heap, heap.length * 2); 
        }
        heap[totalElements] = item;
        int index = totalElements;
        while (index != 0 && (heap[index].compareTo(heap[(index - 1) / 4])) < 0) {
            T store = heap[(index - 1) / 4];
            heap[(index - 1) / 4] = heap[index];
            heap[index] = store;
            index = (index - 1) / 4;
        }
        totalElements++;
    }

    @Override
    public int size() {
        return totalElements;
    }
}
