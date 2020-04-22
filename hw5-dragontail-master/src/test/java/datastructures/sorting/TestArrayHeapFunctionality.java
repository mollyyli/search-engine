package datastructures.sorting;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import misc.BaseTest;
import misc.exceptions.EmptyContainerException;
import datastructures.concrete.ArrayHeap;
import datastructures.interfaces.IPriorityQueue;
import org.junit.Test;

/**
 * See spec for details on what kinds of tests this class should include.
 */
public class TestArrayHeapFunctionality extends BaseTest {
    protected <T extends Comparable<T>> IPriorityQueue<T> makeInstance() {
        return new ArrayHeap<>();
    }

    @Test(timeout = SECOND)
    public void testBasicInsertAndSize() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        heap.insert(1);
        heap.insert(2);
        heap.insert(3);
        heap.insert(5);
        assertEquals(4, heap.size());
    }

    @Test(timeout = SECOND)
    public void testInsertMultipleOrderedElementsAndRemove() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        heap.insert(1);
        heap.insert(2);
        heap.insert(3);
        heap.insert(5);
        heap.insert(10);
        heap.insert(15);
        assertEquals(6, heap.size());
        assertEquals(1, heap.removeMin());
        assertEquals(5, heap.size());
        assertTrue(!heap.isEmpty());
    }

    @Test(timeout = SECOND)
    public void testInsertMultipleUnOrderedElementsAndRemove() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        heap.insert(5);
        heap.insert(7);
        heap.insert(2);
        heap.insert(10);
        heap.insert(15);
        heap.insert(1);
        assertEquals(6, heap.size());
        assertEquals(1, heap.removeMin());
        assertEquals(5, heap.size());
        assertTrue(!heap.isEmpty());

    }

    @Test(timeout = SECOND)
    public void testRemoveMin() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        for (int i = 0; i < 10; i++) {
            heap.insert(i);
        }
        assertEquals(10, heap.size());
        assertEquals(0, heap.removeMin());
        assertEquals(9, heap.size());
    }

    @Test(timeout = SECOND)
    public void testRemoveOneElement() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        heap.insert(5);
        assertEquals(5, heap.removeMin());
        assertEquals(0, heap.size());
    }

    @Test(timeout = SECOND)
    public void testInsertOutOfOrderTwo() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        heap.insert(2);
        heap.insert(8);
        heap.insert(8);
        heap.insert(9);
        heap.insert(15);
        heap.insert(-5);
        heap.insert(-20);
        heap.insert(-25);
        heap.insert(500);
        heap.insert(-500);
        heap.insert(1);
        heap.insert(4325);
        heap.insert(42);
        assertEquals(13, heap.size());
        assertEquals(-500, heap.removeMin());
        assertEquals(-25, heap.removeMin());
        assertEquals(-20, heap.removeMin());
        assertEquals(-5, heap.removeMin());
        assertEquals(1, heap.removeMin());
        assertEquals(2, heap.removeMin());
        assertEquals(7, heap.size());
    }

    @Test(timeout = SECOND)
    public void testMultipleRemoveMin() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        heap.insert(5);
        heap.insert(6);
        heap.insert(9);
        heap.insert(15);
        heap.insert(25);
        heap.insert(30);
        assertEquals(5, heap.removeMin());
        assertEquals(6, heap.removeMin());
        assertEquals(9, heap.removeMin());
        assertEquals(15, heap.removeMin());
        assertEquals(25, heap.removeMin());
        assertEquals(30, heap.removeMin());
    }

    @Test(timeout = SECOND)
    public void testPeekMin() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        heap.insert(3);
        heap.insert(5);
        heap.insert(4);
        heap.insert(2);
        assertEquals(4, heap.size());
        assertEquals(2, heap.peekMin());
        assertEquals(4, heap.size());
        assertEquals(2, heap.removeMin());
        assertEquals(3, heap.size());
        assertEquals(3, heap.peekMin());
    }

    @Test(timeout = SECOND)
    public void testEmptyRemoveMin() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        assertEquals(0, heap.size());
        try {
            heap.removeMin();
            fail("Expected an EmptyContainerException");
        } catch (EmptyContainerException ex) {
            // This is okay, do nothing.
        }
    }

    @Test(timeout = SECOND)
    public void testEmptyPeekMin() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        assertEquals(0, heap.size());
        try {
            heap.peekMin();
            fail("Expected an EmptyContainerException");
        } catch (EmptyContainerException ex) {
            // This is okay, do nothing.
        }
    }

    @Test(timeout = SECOND)
    public void testInsertNull() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        assertEquals(0, heap.size());
        try {
            heap.insert(null);
            fail("Expected an IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // This is okay, do nothing.
        }
    }

    @Test(timeout = SECOND)
    public void testInsertStringsAndPeekAndRemove() {
        IPriorityQueue<String> heap = this.makeInstance();
        heap.insert("Apple");
        heap.insert("Bubble");
        heap.insert("Cat");
        heap.insert("About");
        assertEquals(4, heap.size());
        assertEquals("About", heap.peekMin());
        assertEquals(4, heap.size());
        assertEquals("About", heap.removeMin());
        assertEquals(3, heap.size());
        assertEquals("Apple", heap.peekMin());
        assertEquals("Apple", heap.removeMin());
        assertEquals(2, heap.size());
    }

    @Test(timeout = SECOND)
    public void testInsertAndDeleteAlternatively() {
        IPriorityQueue<String> heap = this.makeInstance();
        heap.insert("Apple");
        heap.insert("Bubble");
        assertEquals(2, heap.size());
        assertEquals("Apple", heap.removeMin());
        assertEquals(1, heap.size());
        assertEquals("Bubble", heap.removeMin());
        assertEquals(0, heap.size());
        for (int i = 0; i < 50; i++) {
            heap.insert("C" + i);
            assertEquals("C" + i, heap.removeMin());
        }
        assertEquals(0, heap.size());
    }

    @Test(timeout = SECOND)
    public void testRemoveMinDuplicates() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        heap.insert(7);
        heap.insert(13);
        heap.insert(8);
        heap.insert(13);
        heap.insert(21);
        heap.insert(14);
        heap.insert(13);
        heap.insert(13);
        heap.insert(20);
        assertEquals(7, heap.removeMin());
        assertEquals(8, heap.removeMin());
        assertEquals(13, heap.removeMin());
    }

    @Test(timeout = SECOND)
    public void testRemoveMinDuplicatesTwo() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        heap.insert(2);
        heap.insert(6);
        heap.insert(8);
        heap.insert(6);
        heap.insert(8);
        heap.insert(7);
        heap.insert(6);
        heap.insert(20);
        assertEquals(2, heap.removeMin());
        assertEquals(6, heap.removeMin());
        assertEquals(6, heap.removeMin());
        assertEquals(6, heap.removeMin());
    }

    @Test(timeout = SECOND)
    public void testInsertOutOfOrder() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        heap.insert(10);
        heap.insert(12);
        heap.insert(14);
        heap.insert(3);
        heap.insert(-1);
        heap.insert(0);
        heap.insert(5);
        heap.insert(-2);
        assertEquals(-2, heap.removeMin());
        assertEquals(-1, heap.removeMin());
        assertEquals(0, heap.removeMin());
        assertEquals(3, heap.removeMin());
    }

    @Test(timeout = SECOND)
    public void testRemoveMinPercolateDown() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        heap.insert(7);
        heap.insert(13);
        heap.insert(11);
        heap.insert(8);
        heap.insert(21);
        heap.insert(54);
        heap.insert(15);
        heap.insert(13);
        heap.insert(39);
        heap.insert(26);
        heap.insert(20);
        heap.insert(48);
        heap.insert(12);
        heap.insert(9);
        heap.insert(12);
        heap.insert(10);
        heap.insert(23);
        assertEquals(7, heap.removeMin());
        assertEquals(8, heap.removeMin());
        assertEquals(9, heap.removeMin());
        assertEquals(10, heap.removeMin());
        assertEquals(11, heap.removeMin());
    }
}
