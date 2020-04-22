package datastructures.sorting;

import misc.BaseTest;
import datastructures.concrete.DoubleLinkedList;
import datastructures.interfaces.IList;
import misc.Searcher;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * See spec for details on what kinds of tests this class should include.
 */

public class TestTopKSortFunctionality extends BaseTest {

    @Test(timeout = SECOND)
    public void testSortedOrder() {
        IList<Integer> list = new DoubleLinkedList<>();
        for (int i = 0; i < 20; i++) {
            list.add(i);
        }
        IList<Integer> top = Searcher.topKSort(5, list);
        assertEquals(5, top.size());
        for (int i = 0; i < top.size(); i++) {
            assertEquals(15 + i, top.get(i));
        }
    }

    @Test(timeout = SECOND)
    public void testEmptyList() {
        IList<Integer> list = new DoubleLinkedList<>();
        IList<Integer> top = Searcher.topKSort(0, list);
        assertEquals(0, top.size());
    }

    @Test(timeout = SECOND)
    public void testNegativeK() {
        IList<Integer> list = new DoubleLinkedList<>();
        try {
            IList<Integer> top = Searcher.topKSort(-1, list);
            fail("Expected an IllegalArgumentrException");
        } catch (IllegalArgumentException ex) {
            // This is okay, do nothing.
        }
    }

    @Test(timeout = SECOND)
    public void testKIsLargerThanSize() {
        IList<Integer> list = new DoubleLinkedList<>();
        List<Integer> list2 = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(9 - i);
            list2.add(i);
        }
        IList<Integer> top = Searcher.topKSort(21, list);
        for (int i = 0; i < 10; i++) {
            assertEquals(list2.get(i), top.get(i));
        }
    }

    @Test(timeout = SECOND)
    public void listContainsStringsNotSorted() {
        IList<String> list = new DoubleLinkedList<>();
        list.add("Apple");
        list.add("Bubble");
        list.add("Cat");
        list.add("Dog");
        list.add("About");
        IList<String> top = Searcher.topKSort(3, list);
        assertEquals(3, top.size());
        assertEquals("Bubble", top.get(0));
        assertEquals("Cat", top.get(1));
        assertEquals("Dog", top.get(2));
    }

    @Test(timeout = SECOND)
    public void listNotSortedOrder() {
        IList<Integer> list = new DoubleLinkedList<>();
        list.add(4);
        list.add(20);
        list.add(3);
        list.add(7);
        list.add(11);
        IList<Integer> top = Searcher.topKSort(3, list);
        assertEquals(3, top.size());
        assertEquals(7, top.get(0));
        assertEquals(11, top.get(1));
        assertEquals(20, top.get(2));
    }

    @Test(timeout = SECOND)
    public void testDuplicatesInteger() {
        IList<Integer> list = new DoubleLinkedList<>();
        for (int i = 0; i < 10; i++) {
            list.add(1);
        }
        IList<Integer> top = Searcher.topKSort(3, list);
        assertEquals(3, top.size());
        for (int i = 0; i < top.size(); i++) {
            assertEquals(1, top.get(i));
        }
    }

    @Test(timeout = SECOND)
    public void testIfModifytheList() {
        IList<Integer> list = new DoubleLinkedList<>();
        List<Integer> list2 = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add(20 - i);
            list2.add(20 - i);
        }
        IList<Integer> top = Searcher.topKSort(3, list);
        for (int i = 0; i < 20; i++) {
            assertEquals(list.get(i), list2.get(i));
        }
    }
}