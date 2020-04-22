package datastructures.dictionaries;

//import static org.junit.Assert.assertTrue;
//import static org.junit.Assert.assertFalse;
//import org.junit.Assert;

import datastructures.concrete.dictionaries.ChainedHashDictionary;
//import datastructures.concrete.KVPair;
import datastructures.interfaces.IDictionary;
import org.junit.Test;

public class TestChainedHashDictionary extends TestDictionary {

    protected <K, V> IDictionary<K, V> newDictionary() {
        return new ChainedHashDictionary<>();
    }

    /**
     * Any double linked list tests you write should be in this file.
     *
     * You may copy any required helper methods from TestProvidedChainedHashDictionary.java
     *
     */

    @Test
    public void testBasic() {
        // Place holder test.
    }
}
