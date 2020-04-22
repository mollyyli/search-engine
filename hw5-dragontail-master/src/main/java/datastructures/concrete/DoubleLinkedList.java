package datastructures.concrete;

import datastructures.interfaces.IList;
import misc.exceptions.EmptyContainerException;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Note: For more info on the expected behavior of your methods, see the source
 * code for IList.
 */
public class DoubleLinkedList<T> implements IList<T> {
    // You may not rename these fields or change their types.
    // We will be inspecting these in our private tests.
    // You also may not add any additional fields.
    private Node<T> front;
    private Node<T> back;
    private int size;

    public DoubleLinkedList() {
        this.front = null;
        this.back = null;
        this.size = 0;
    }

    @Override
    public void add(T item) {
        if (front == null) { // adding when no elements are present
            front = new Node<T>(item);
            back = front;
        } else {
            back.next = new Node<T>(back, item, null);
            back = back.next;
        }
        size++;
    }

    @Override
    public T remove() {
        if (size == 0) {
            throw new EmptyContainerException();
        }
        T item = back.data;
        if (size == 1) { // removing with only one element
            front = null;
            back = null;
        } else {
            back = back.prev;
            back.next = null;
        }
        size--;
        return item;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> temp = front;
        for (int i = 0; i < index; i++) {
            temp = temp.next;
        }
        return temp.data;
    }

    @Override
    public void set(int index, T item) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        if (index == 0) { // front case
            front = new Node<T>(null, item, front.next);
            if (front.next != null) {
                front.next.prev = front;
            }
        } else {
            Node<T> temp = front;
            for (int i = 0; i < index - 1; i++) {
                temp = temp.next;
            }
            temp.next = new Node<T>(temp, item, temp.next.next);
            if (temp.next.next != null) { // middle case
                temp.next.next.prev = temp.next;
            } else { // end case
                back = temp.next;
            }
        }
    }

    @Override
    public void insert(int index, T item) {
        if (index < 0 || index >= size + 1) {
            throw new IndexOutOfBoundsException();
        }
        if (index == 0) { // front case
            startFromFront(index, item);
        } else if (index == size) { // end case
            back = new Node<T>(back, item, null);
            back.prev.next = back;
        } else {
            startFromBack(index, item);
        }
        size++;
    }

    private void startFromFront(int index, T item) {
        front = new Node<T>(null, item, front);
        if (front.next == null) {
            back = front;
        } else {
            front.next.prev = front;
        }
    }
    
    private void startFromBack(int index, T item) {
        Node<T> temp = back;
        if (index >= size / 2) { // start from back
            for (int i = 0; i < size - index; i++) {
                temp = temp.prev;
            }
        } else { // start from front
            temp = front;
            for (int i = 0; i < index - 1; i++) {
                temp = temp.next;
            }
        }
        temp.next = new Node<T>(temp, item, temp.next);
        temp.next.next.prev = temp.next;
    }

    @Override
    public T delete(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        T item = null;
        if (index < size / 2 + 1) { // delete any node from first half
            Node<T> temp = front;
            for (int i = 0; i < index - 1; i++) {
                temp = temp.next;
            }
            if (index == 0) { // front case
                item = deleteFrontCase(item, temp);
            } else { // middle case for the first half
                item = deleteFirstHalf(item, temp);
            }
        } else { // delete any node from latter half
            item = deleteLatterHalf(index, item);
        }
        size--;
        return item;
    }

    private T deleteFrontCase(T item, Node<T> temp) {
        item = temp.data;
        if (front.next != null) { // delete the first element
            front = temp.next;
            front.prev = null;
        } else { // delete the first and only element
            front = null;
            back = null;
        }
        return item;
    }

    private T deleteFirstHalf(T item, Node<T> temp) {
        item = temp.next.data;
        temp.next = temp.next.next;
        if (temp.next != null) { // more than two elements
            temp.next.prev = temp;
        } else {
            back = front;
        }
        return item;
    }

    private T deleteLatterHalf(int index, T item) {
        Node<T> temp = back;
        for (int i = 0; i < size - index + 1; i++) {
            temp = temp.prev;
        }
        if (index != size - 1) {
            item = temp.next.data;
            temp.next = temp.next.next;
            temp.next.prev = temp;
        } else {
            item = back.data;
            back = back.prev;
            back.next = null;
        }
        return item;
    }

    @Override
    public int indexOf(T item) {
        int index = 0;
        Node<T> temp = front;
        while (temp != null) {
            if ((item == null && temp.data == null) || 
                    (temp.data != null && temp.data.equals(item))) {
                return index;
            }
            index++;
            temp = temp.next;
        }
        return -1;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean contains(T other) {
        return indexOf(other) != -1;
    }

    @Override
    public Iterator<T> iterator() {
        // Note: we have provided a part of the implementation of
        // an iterator for you. You should complete the methods stubs
        // in the DoubleLinkedListIterator inner class at the bottom
        // of this file. You do not need to change this method.
        return new DoubleLinkedListIterator<>(this.front);
    }

    private static class Node<E> {
        // You may not change the fields in this node or add any new fields.
        public final E data;
        public Node<E> prev;
        public Node<E> next;

        public Node(Node<E> prev, E data, Node<E> next) {
            this.data = data;
            this.prev = prev;
            this.next = next;
        }

        public Node(E data) {
            this(null, data, null);
        }

        // Feel free to add additional constructors or methods to this class.
    }

    private static class DoubleLinkedListIterator<T> implements Iterator<T> {
        // You should not need to change this field, or add any new fields.
        private Node<T> current;

        public DoubleLinkedListIterator(Node<T> current) {
            // You do not need to make any changes to this constructor.
            this.current = current;
        }

        /**
         * Returns 'true' if the iterator still has elements to look at; returns 'false'
         * otherwise.
         */
        public boolean hasNext() {
            return current != null;
        }

        /**
         * Returns the next item in the iteration and internally updates the iterator to
         * advance one element forward.
         *
         * @throws NoSuchElementException if we have reached the end of the iteration
         *                                and there are no more elements to look at.
         */
        public T next() {
            if (current == null) {
                throw new NoSuchElementException();
            }
            T item = current.data;
            current = current.next;
            return item;
        }
    }
}