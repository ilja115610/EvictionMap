package evictionMapSingleThread;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.PriorityBlockingQueue;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class EvictionMap <K,V> {

    private final Map<K, V> internalMap = new ConcurrentHashMap<>();

    private final PriorityBlockingQueue<ExpiryRecord<K>> records = new PriorityBlockingQueue<>();

    private final long durationMS;

    public EvictionMap(long durationMS) {
        this.durationMS = MILLISECONDS.toNanos(durationMS);
    }


    public void put(K key, V value) {

        internalMap.put(key, value);
        records.put(new ExpiryRecord<>(System.nanoTime(), key));
    }


    public V get(K key) {
        cleanUp();
        return internalMap.get(key);
    }

    private void cleanUp() {

        for(int i = 0; i < records.size(); i++){
            ExpiryRecord<K> oldest = records.peek();
            if(oldest!=null&&(oldest.getTimeStamp()+durationMS)<System.nanoTime()){
                try {
                    records.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                internalMap.remove(oldest.getKey());
            }
        }
    }
    }

