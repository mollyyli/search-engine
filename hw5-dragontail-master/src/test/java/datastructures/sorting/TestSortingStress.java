package datastructures.sorting;

import misc.BaseTest;
import misc.Searcher;

import java.util.Iterator;

import org.junit.Test;

import datastructures.concrete.ArrayHeap;
import datastructures.concrete.DoubleLinkedList;
import datastructures.interfaces.IList;
import datastructures.interfaces.IPriorityQueue;

/**
 * See spec for details on what kinds of tests this class should include.
 */
public class TestSortingStress extends BaseTest {
    protected <T extends Comparable<T>> IPriorityQueue<T> makeInstance() {
        return new ArrayHeap<>();
    }

    @Test(timeout = 10 * SECOND)
    public void testInsertEfficiency() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        for (int i = 0; i < 1000000; i++) {
            heap.insert(i);
        }
        assertEquals(1000000, heap.size());
    }

    @Test(timeout = 10 * SECOND)
    public void testInsertAndRemoveEfficiency() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        for (int i = 0; i < 1000000; i++) {
            heap.insert(i);
        }
        assertEquals(1000000, heap.size());
        for (int i = 0; i < 1000000; i++) {
            assertEquals(i, heap.removeMin());
        }
        assertEquals(0, heap.size());
    }

    @Test(timeout = 10 * SECOND)
    public void testInsertAndPeekEfficiency() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        for (int i = 0; i < 1000000; i++) {
            heap.insert(i);
        }
        for (int i = 0; i < 1000000; i++) {
            assertEquals(0, heap.peekMin());
        }
        assertEquals(1000000, heap.size());
    }

    @Test(timeout = 10 * SECOND)
    public void testInsertFromLargestToSmallestAndRemoveEfficiency() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        for (int i = 1000000; i > 0; i--) {
            heap.insert(i);
        }
        for (int i = 0; i < 1000000; i++) {
            assertEquals(i + 1, heap.removeMin());
        }
        assertEquals(0, heap.size());
    }

    @Test(timeout = 10 * SECOND)
    public void testInsertLargeAndSmallValuesAltnerativelyEfficiency() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        for (int i = 0; i < 1000000; i++) {
            if (i % 2 == 0) {
                heap.insert(i * 2);
            } else {
                heap.insert(i / 2);
            }
        }
        assertEquals(1000000, heap.size());
    }

    @Test(timeout = 10 * SECOND)
    public void testInsertValuesAndRemoveAlternativelyEfficiency() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        for (int i = 0; i < 1000000; i++) {
            heap.insert(i);
            if (i % 4 == 0) {
                assertEquals(i / 4, heap.removeMin());
            }
        }
        assertEquals(750000, heap.size());
    }

    @Test(timeout = 10 * SECOND)
    public void testTopKSorted() {
        IList<Integer> list = new DoubleLinkedList<>();
        int cap = 500000;
        for (int i = 0; i < cap; i++) {
            list.add(i);
        }
        IList<Integer> top = Searcher.topKSort(1000, list);
        assertEquals(1000, top.size());
        Iterator<Integer> itr2 = top.iterator();
        int j = 0;
        while (itr2.hasNext()) {
            assertEquals(cap - 1000 + j, itr2.next());
            j++;
        }
    }

    @Test(timeout = 10 * SECOND)
    public void testTopKNotSorted() {
        IList<Integer> list = new DoubleLinkedList<>();
        int cap = 1000000;
        for (int i = 0; i < cap; i++) {
            list.add(cap - i);
        }
        IList<Integer> top = Searcher.topKSort(1000, list);
        assertEquals(1000, top.size());
        Iterator<Integer> itr2 = top.iterator();
        int j = 1;
        while (itr2.hasNext()) {
            assertEquals(999000 + j, itr2.next());
            j++;
        }
    }

    @Test(timeout = 10 * SECOND)
    public void testMultipleSortedTopKEfficiency() {
        IList<Integer> list = new DoubleLinkedList<>();
        int cap = 500000;
        for (int i = 0; i < cap; i++) {
            list.add(i);
        }
        IList<Integer> top = null;
        for (int i = 0; i < 20; i++) {
            top = Searcher.topKSort(500000, list);
        }
        int j = 0;
        Iterator<Integer> itr2 = top.iterator();
        while (itr2.hasNext()) {
            assertEquals(j, itr2.next());
            j++;
        }
    }
}