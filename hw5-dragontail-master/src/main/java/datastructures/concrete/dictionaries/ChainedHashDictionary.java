package datastructures.concrete.dictionaries;

import datastructures.concrete.KVPair;
import datastructures.interfaces.IDictionary;
import misc.exceptions.NoSuchKeyException;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * See the spec and IDictionary for more details on what each method should do
 */
public class ChainedHashDictionary<K, V> implements IDictionary<K, V> {
    // You may not change or rename this field: we will be inspecting
    // it using our private tests.
    private IDictionary<K, V>[] chains;
    private int sizeOfDict;
    private int total;

    // You're encouraged to add extra fields (and helper methods) though!

    public ChainedHashDictionary() {
        chains = makeArrayOfChains(10);
        sizeOfDict = 10;
        total = 0;
    }

    /**
     * This method will return a new, empty array of the given size that can contain
     * IDictionary<K, V> objects.
     *
     * Note that each element in the array will initially be null.
     */
    @SuppressWarnings("unchecked")
    private IDictionary<K, V>[] makeArrayOfChains(int size) {
        // Note: You do not need to modify this method.
        // See ArrayDictionary's makeArrayOfPairs(...) method for
        // more background on why we need this method.
        return (IDictionary<K, V>[]) new IDictionary[size];
    }

    @Override
    public V get(K key) {
        int bucketNumber = 0;
        if (key != null) {
            bucketNumber = Math.abs(key.hashCode() % sizeOfDict);
        }
        if (chains[bucketNumber] != null) {
            return chains[bucketNumber].get(key);
        }
        throw new NoSuchKeyException();
    }

    @Override
    public void put(K key, V value) {
        int bucketNumber = 0;
        double loadFactor = (double) total / sizeOfDict;
        if (loadFactor > 1) {
            int newSize = 2 * chains.length;
            IDictionary<K, V>[] newChains = makeArrayOfChains(newSize);
            int newBucketNumber = 0;
            for (KVPair<K, V> var : this) {
                newBucketNumber = (Math.abs(var.getKey().hashCode())) % newSize;
                if (newChains[newBucketNumber] == null) {
                    newChains[newBucketNumber] = new ArrayDictionary<K, V>();
                }
                newChains[newBucketNumber].put(var.getKey(), var.getValue());
            }
            chains = newChains;
            sizeOfDict = newSize;
        }
        if (key != null) {
            bucketNumber = Math.abs(key.hashCode() % sizeOfDict);
        }
        if (chains[bucketNumber] == null) {
            chains[bucketNumber] = new ArrayDictionary<K, V>();
        }
        if (!chains[bucketNumber].containsKey(key)) {
            total++;
        }
        chains[bucketNumber].put(key, value);
    }

    @Override
    public V remove(K key) {
        int bucketNumber = 0;
        if (key != null) {
            bucketNumber = Math.abs(key.hashCode() % sizeOfDict);
        }
        if (chains[bucketNumber] != null) {
            total--;
            return chains[bucketNumber].remove(key);
        }
        throw new NoSuchKeyException();
    }

    @Override
    public boolean containsKey(K key) {
        int bucketNumber = 0;
        if (key != null) {
            bucketNumber = Math.abs(key.hashCode() % sizeOfDict);
        }
        if (chains[bucketNumber] != null) {
            return chains[bucketNumber].containsKey(key);
        }
        return false;
    }

    @Override
    public int size() {
        return total;
    }

    @Override
    public Iterator<KVPair<K, V>> iterator() {
        // Note: you do not need to change this method
        return new ChainedIterator<>(this.chains);

    }

    /**
     * Hints:
     *
     * 1. You should add extra fields to keep track of your iteration state. You can
     * add as many fields as you want. If it helps, our reference implementation
     * uses three (including the one we gave you).
     *
     * 2. Before you try and write code, try designing an algorithm using pencil and
     * paper and run through a few examples by hand.
     *
     * We STRONGLY recommend you spend some time doing this before coding. Getting
     * the invariants correct can be tricky, and running through your proposed
     * algorithm using pencil and paper is a good way of helping you iron them out.
     *
     * 3. Think about what exactly your *invariants* are. As a reminder, an
     * *invariant* is something that must *always* be true once the constructor is
     * done setting up the class AND must *always* be true both before and after you
     * call any method in your class.
     *
     * Once you've decided, write them down in a comment somewhere to help you
     * remember.
     *
     * You may also find it useful to write a helper method that checks your
     * invariants and throws an exception if they're violated. You can then call
     * this helper method at the start and end of each method if you're running into
     * issues while debugging.
     *
     * (Be sure to delete this method once your iterator is fully working.)
     *
     * Implementation restrictions:
     *
     * 1. You **MAY NOT** create any new data structures. Iterators are meant to be
     * lightweight and so should not be copying the data contained in your
     * dictionary to some other data structure.
     *
     * 2. You **MAY** call the `.iterator()` method on each IDictionary instance
     * inside your 'chains' array, however.
     */

    private static class ChainedIterator<K, V> implements Iterator<KVPair<K, V>> {
        private IDictionary<K, V>[] chains;
        private int current;
        private Iterator<KVPair<K, V>> itr;
        private boolean currentIterator;

        public ChainedIterator(IDictionary<K, V>[] chains) {
            this.chains = chains;
            current = 0;
        }

        @Override
        public boolean hasNext() {
            for (int i = current; i < chains.length; i++) {
                if (chains[i] != null) {
                    current = i;
                    if (!currentIterator) {
                        itr = chains[current].iterator();
                        currentIterator = true;
                    }
                    if (itr.hasNext()) {
                        return true;
                    }
                    currentIterator = false;
                }
            }
            current = chains.length;
            return false;
        }

        @Override
        public KVPair<K, V> next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return itr.next();
        }
    }
}