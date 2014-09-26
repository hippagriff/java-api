package com.hippagriff.cache;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

/**
 * Implentation of hazelcast cache.
 * 
 * @author jon
 * 
 */
public class HazelcastCacheImpl implements CacheInterface
{
    // 30 minutes
    private Integer DEFAULT_CACHE_EXP_TIME_SECONDS = 1800;

    @Autowired
    private Config config = null;

    private HazelcastInstance hazelcast = null;

    // private ConcurrentMap<String,Object> m = null;

    private IMap<String,Object> map = null;

    private String HAZELCAST_CACHE_NAME = "hippagriff1";

    @Override
    public boolean add(String key, Object value)
    {
        map.put(key, value);
        return true;
    }

    @Override
    public boolean add(String key, Object value, Integer expiryTimeSeconds)
    {
        map.put(key, value, expiryTimeSeconds, TimeUnit.SECONDS);
        return true;
    }

    @Override
    public boolean replace(String key, Object value)
    {
        return map.replace(key, value) != null;
    }

    @Override
    public <T> boolean replaceTypeSafe(String key, T value)
    {
        Object o = get(key);
        if (o != null)
        {
            try
            {
                @SuppressWarnings({ "unused", "unchecked" })
                T temp = (T) o;
            }
            catch (ClassCastException e)
            {
                return false;
            }
            replace(key, value);
        }
        return false;
    }

    @Override
    public <T> boolean replaceTypeSafe(String key, T value, Integer expiryTime)
    {
        return replaceTypeSafe(key, value);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T get(String key)
    {
        return (T) map.get(key);
    }

    @Override
    public void touch(String key)
    {
    }

    @Override
    public void touch(String key, Integer expiryTime)
    {
    }

    @Override
    public <T> T getAndTouch(String key)
    {
        return get(key);
    }


    @Override
    public boolean remove(String key)
    {
        return map.remove(key)!=null;
    }
    
    @Override
    public void flush()
    {
        map.clear();
    }

    @Override
    public void initializeCache()
    {
        if (config == null)
            config = new Config();

        hazelcast = Hazelcast.newHazelcastInstance(config);

        map = hazelcast.getMap(HAZELCAST_CACHE_NAME);
    }

    @Override
    public void destroyCache()
    {
        hazelcast.shutdown();
    }
}
