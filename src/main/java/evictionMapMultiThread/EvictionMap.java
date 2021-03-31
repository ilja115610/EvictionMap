package evictionMapMultiThread;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.PriorityBlockingQueue;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class EvictionMap<K,V> implements ExpiryMap<K,V> {


    private final Map<K,V> internalMap = new ConcurrentHashMap<>();

    private final PriorityBlockingQueue<ExpiryRecord<K>> records = new PriorityBlockingQueue<>();

    private final long durationMS;


    public EvictionMap (long durationMS) {

        this.durationMS = MILLISECONDS.toNanos(durationMS);
        startTracking();
    }


    @Override
    public void put (K key, V value) {

            records.put(new ExpiryRecord<>(System.nanoTime(), key));
            internalMap.put(key, value);
            synchronized (this){
                notifyAll();
            }
    }

    private void startTracking() {

        Thread thread = new Thread(()->{
                while(true) {
                    cleanMap();
                    if(internalMap.isEmpty()) {
                        try {
                            synchronized (this) {
                                wait();
                            }
                        } catch (InterruptedException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                }
        });
        thread.setDaemon(true);
        thread.start();
    }

    private void cleanMap () {

        while(records.size()>0){
            ExpiryRecord<K> oldest = records.peek();
            if((oldest.getTimeStamp()+durationMS)<System.nanoTime()){
                try {
                    records.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                internalMap.remove(oldest.getKey());
            }
            else {
                break;
            }
        }
    }

    @Override
    public V get(K key){
        return internalMap.get(key);
    }



    public int size() {
        return internalMap.size();
    }




}
