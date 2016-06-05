package gson;

/**
 * Created by ces_m on 6/4/2016.
 */
public class Param<K,V> {

    private final K key;
    private final V value;

    public Param(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() { return key; }
    public V getValue() { return value; }

    @Override
    public int hashCode() { return key.hashCode() ^ value.hashCode(); }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Param)) return false;
        Param paramo = (Param) o;
        return this.key.equals(paramo.getKey()) &&
                this.value.equals(paramo.getValue());
    }

}