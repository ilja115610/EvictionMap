package evictionMapMultiThread;

public interface ExpiryMap<K,V> {


    /**
     *  Creates value with specified key in this map. If the map previously contained a
     * 	value associated with key, the old value is replaced by value.
     *
     * @param key
     * @param value
     */
    void put(K key, V value);

    /**
     * Returns the value associated with key in this map or null if no mapping exists.
     *
     * @param key
     * @return
     */

    V get(K key);

}
