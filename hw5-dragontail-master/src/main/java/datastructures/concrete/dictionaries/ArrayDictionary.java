package datastructures.concrete.dictionaries;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

import datastructures.concrete.KVPair;
import datastructures.interfaces.IDictionary;
import misc.exceptions.NoSuchKeyException;

/**
 * See IDictionary for more details on what this class should do
 */
public class ArrayDictionary<K, V> implements IDictionary<K, V> {
    // You may not change or rename this field: we will be inspecting
    // it using our private tests.
    private Pair<K, V>[] pairs;
    private int total;
    private int size;

    // You're encouraged to add extra fields (and helper methods) though!
    public ArrayDictionary() {
        pairs = makeArrayOfPairs(3);
        total = 3;
        size = 0;
    }

    /**
     * This method will return a new, empty array of the given size that can contain
     * Pair<K, V> objects.
     *
     * Note that each element in the array will initially be null.
     */
    @SuppressWarnings("unchecked")
    private Pair<K, V>[] makeArrayOfPairs(int arraySize) {
        // It turns out that creating arrays of generic objects in Java
        // is complicated due to something known as 'type erasure'.
        //
        // We've given you this helper method to help simplify this part of
        // your assignment. Use this helper method as appropriate when
        // implementing the rest of this class.
        //
        // You are not required to understand how this method works, what
        // type erasure is, or how arrays and generics interact. Do not
        // modify this method in any way.
        return (Pair<K, V>[]) (new Pair[arraySize]);
    }

    @Override
    public V get(K key) {
        for (int i = 0; i < size; i++) {
            if ((key == null && pairs[i].key == null) || 
                    (pairs[i].key != null && pairs[i].key.equals(key))) {
                return pairs[i].value;
            }
        }
        throw new NoSuchKeyException();
    }

    @Override
    public void put(K key, V value) {
        boolean foundKey = false;
        for (int i = 0; i < size; i++) {
            if ((key == null && pairs[i].key == null) || 
                    (pairs[i].key != null && pairs[i].key.equals(key))) {
                pairs[i].value = value;
                foundKey = true;
                break;
            }
        }
        if (!foundKey) {
            if (size == total) {
                total = total * 2;
                pairs = Arrays.copyOf(pairs, total);
            }
            pairs[size] = new Pair<K, V>(key, value);
            size++;
        }
    }

    @Override
    public V remove(K key) {
        for (int i = 0; i < size; i++) {
            if ((key == null && pairs[i].key == null) || 
                    (pairs[i].key != null && pairs[i].key.equals(key))) {
                V item = pairs[i].value;
                if (i == size) {
                    pairs[i] = null;
                } else {
                    pairs[i].key = pairs[size - 1].key;
                    pairs[i].value = pairs[size - 1].value;
                    pairs[size - 1] = null;
                }
                size--;
                return item;
            }
        }
        throw new NoSuchKeyException();
    }

    @Override
    public boolean containsKey(K key) {
        for (int i = 0; i < size; i++) {
            if ((key == null && pairs[i].key == null) || 
                    (pairs[i].key != null && pairs[i].key.equals(key))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }
    
    @Override
    public Iterator<KVPair<K, V>> iterator() {
        return new ArrayDictionaryIterator<>(pairs, total);
    }

    private static class ArrayDictionaryIterator<K, V> implements Iterator<KVPair<K, V>> {
        private KVPair<K, V> keyValuePair;
        private Pair<K, V>[] pairs;
        private int size;
        private int total;

        public ArrayDictionaryIterator(Pair<K, V>[] dictionaryPair, int total) {
            this.total = total;
            size = 0;
            pairs = dictionaryPair;
        }

        @Override
        public boolean hasNext() {
            if (size != total && pairs[size] == null) {
                return false;
            }
            return size != total;
        }

        @Override
        public KVPair<K, V> next() {
            if (size != total && pairs[size] == null) {
                throw new NoSuchElementException();
            }
            if (size == total) {
                throw new NoSuchElementException();
            }
            keyValuePair = new KVPair<K, V>(pairs[size].key, pairs[size].value);
            size++;
            return keyValuePair;
        }
    }

    private static class Pair<K, V> {
        public K key;
        public V value;

        // You may add constructors and methods to this class as necessary.
        public Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String toString() {
            return this.key + "=" + this.value;
        }
    }
}