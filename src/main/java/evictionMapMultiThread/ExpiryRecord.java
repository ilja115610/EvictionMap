package evictionMapMultiThread;

public class ExpiryRecord<K> implements Comparable<ExpiryRecord<K>>{

    private Long timeStamp;

    private K key;

    public ExpiryRecord(long timeStamp, K key) {
        this.timeStamp = timeStamp;
        this.key = key;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public K getKey() {
        return key;
    }

    @Override
    public int compareTo(ExpiryRecord<K> o) {
        return Long.compare(this.timeStamp,o.timeStamp);
    }
}
