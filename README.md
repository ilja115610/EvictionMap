# EvictionMap - Playtech internship 2021

## Assignment: 
Implement generic structure  EvictionMap<K, V> that acts as key-value map with following time-based eviction
policy:  expire entries after the specified duration has passed since the entry was created,
or the most recent replacement of the value. Specify duration as EvictionMap constructor parameter.

##

### There are 2 different implementations:

* Multithreading solution - ConcurrentHashMap is used as internal map to store Key-Value pairs.
Also, PriorityBlockingQueue used internally to store Timestamps and Keys. The queue is ordered by the entry's expiry time, so the oldest items move to the head of the queue.
  Both internal data structures are thread-safe.
  Once map is initialized, separate thread starts and constantly tracks expiring items in the queue, if expiry interval for element is exceeded - related record removed from the map and from the queue.
  DISADVANTAGE of this solution is that in case of high load (many GETs and PUTs) it is NOT guaranteed that EXPIRED item will NOT be retrieved.
  This can happen in case of any interruption in another thread or if the thread which is tracking and removing items will be late even for 1 nanosecond.
  

* Single-thread solution - in this solution ConcurrentHashMap and PriorityBlockingQueue are also used to provide thread-safety.
ADVANTAGE of this solution is that it guarantees NO expired entries will be retrieved from the map.
  This achieved because before any GET operation - a method called to search for any expired entry and remove it.
  
Synchronisation of PUT and GET methods did not seem to be a good idea as it would decrease the performance of EvictionMap and neglect all benefits of concurrent collections.

### Used tools & Technologies:

* Java 12 + Maven

* JUnit 4.13.1

* AssertJ Core 3.19.0

### Installation 

#### Clone repository 

`https://github.com/ilja115610/EvictionMap.git`

### Project build

`mvn clean install`

### @Testing

`mvn clean test`
