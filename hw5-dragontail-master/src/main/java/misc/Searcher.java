package misc;

import datastructures.concrete.ArrayHeap;
import datastructures.concrete.DoubleLinkedList;
import datastructures.interfaces.IList;
import datastructures.interfaces.IPriorityQueue;

public class Searcher {
    /**
     * This method takes the input list and returns the top k elements in sorted
     * order.
     *
     * So, the first element in the output list should be the "smallest" element;
     * the last element should be the "biggest".
     *
     * If the input list contains fewer then 'k' elements, return a list containing
     * all input.length elements in sorted order.
     *
     * This method must not modify the input list.
     *
     * @throws IllegalArgumentException if k < 0
     */
    public static <T extends Comparable<T>> IList<T> topKSort(int k, IList<T> input) {
        // Implementation notes:
        //
        // - This static method is a _generic method_. A generic method is similar to
        // the generic methods we covered in class, except that the generic parameter
        // is used only within this method.
        //
        // You can implement a generic method in basically the same way you implement
        // generic classes: just use the 'T' generic type as if it were a regular type.
        //
        // - You should implement this method by using your ArrayHeap for the sake of
        // efficiency.

        if (k < 0) {
            throw new IllegalArgumentException();
        }
        IList<T> store = new DoubleLinkedList<>();
        if (k == 0) {
            return store;
        }
        IPriorityQueue<T> obtainMin = new ArrayHeap<>();
        if (input.size() < k) {
            k = input.size();
        }
        for (T element : input) {
            if (obtainMin.size() == k + 1) {
                obtainMin.removeMin();
            }
            obtainMin.insert(element);
        }
        if (k != input.size()) {
            obtainMin.removeMin();
        }
        while (!obtainMin.isEmpty()) {
            store.add(obtainMin.removeMin());
        }
        return store;
    }
}