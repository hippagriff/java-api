package com.hippagriff.cache;

/**
 * Basic interface for inserting and reading from a cache layer.
 * 
 * @author jon
 * 
 */
public interface CacheInterface
{

    /**
     * 
     * @param key
     * @param value
     * @return
     */
    boolean add(String key, Object value);

    /**
     * @param key
     * @param value
     * @param expiryTime
     * @return true/false Add an object to cache with the corresponding key which will expire from cache in expiryTime
     */
    boolean add(String key, Object value, Integer expiryTime);

    /**
     * 
     * @param key
     * @param value
     * @return
     */
    boolean replace(String key, Object value);

    /**
     * @param key
     * @param value
     * @return true/false Typesafe Replace an object to cache with the corresponding key
     */
    <T> boolean replaceTypeSafe(String key, T value);

    /**
     * @param key
     * @param value
     * @param expiryTime
     * @return true/false Typesafe Replace an object to cache with the corresponding key which will expire from cache in
     *         expiryTime
     */
    <T> boolean replaceTypeSafe(String key, T value, Integer expiryTime);

    /**
     * @param key
     * @return generic object from cache stored with corresponding key value
     */
    <T> T get(String key);

    /**
     * @param key Update the time to expire of the object stored at key
     */
    void touch(String key);

    /**
     * @param key Update the time to expire of the object stored at key with new expiryTime
     */
    void touch(String key, Integer expiryTime);

    /**
     * @param key
     * @return generic Object Update the time to expire of the object stored at key and return object
     */
    <T> T getAndTouch(String key);

    /**
     * 
     * @param key
     * @return
     */
    boolean remove(String key);

    /**
     * Flushes the cache and clears all contents
     */
    void flush();

    void initializeCache();

    void destroyCache();

}
