package evictionMapMultiThread;
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class EvictionMapTest {


    private long keyLifeTimeMs;

    @Test
    public void putAndRetrieveValues() {

        keyLifeTimeMs = 5000;

        ExpiryMap<String, String> map = new EvictionMap<>(keyLifeTimeMs);

        map.put("key1", "value1");
        map.put("key2", "value2");

        assertThat(map.get("key1")).isEqualTo("value1");
        assertThat(map.get("key2")).isEqualTo("value2");

    }

    @Test
    public void putAndRetrieveThenWaitToExpireAndRetrieve() throws InterruptedException {

        keyLifeTimeMs = 10000;

        ExpiryMap<String, String> map = new EvictionMap<>(keyLifeTimeMs);

        map.put("key1", "value1");
        map.put("key2", "value2");

        assertThat(map.get("key1")).isEqualTo("value1");
        assertThat(map.get("key2")).isEqualTo("value2");

        Thread.sleep(11000);

        assertThat(map.get("key1")).isNull();
        assertThat(map.get("key2")).isNull();

    }

    @Test
    public void putAndWaitToExpire() throws InterruptedException {

        keyLifeTimeMs = 5000;

        ExpiryMap<String, String> map = new EvictionMap<>(keyLifeTimeMs);

        map.put("key1", "value1");
        map.put("key2", "value2");

        Thread.sleep(7000);

        assertThat(map.get("key1")).isNull();
        assertThat(map.get("key2")).isNull();

    }

    @Test
    public void severalAdditionsAndRetrievals() throws InterruptedException {

        keyLifeTimeMs = 10000;

        ExpiryMap<String,String> map = new EvictionMap<>(keyLifeTimeMs);

        map.put("key1", "value1");
        map.put("key2", "value2");

        Thread.sleep(3000);

        assertThat(map.get("key1")).isEqualTo("value1");
        assertThat(map.get("key2")).isEqualTo("value2");

        map.put("key3", "value3");

        Thread.sleep(8000);

        assertThat(map.get("key1")).isNull();
        assertThat(map.get("key2")).isNull();

        assertThat(map.get("key3")).isEqualTo("value3");
    }



}
