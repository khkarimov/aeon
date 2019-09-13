package com.ultimatesoftware.aeon.core.common.helpers;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * This class allows for instantiation of a HashMap whose entries passively expire
 * after a given amount of time.
 *
 * @param <K> The type of keys in this map
 * @param <V> The type of values in this map
 */
public class PassiveExpiringMap<K, V>
        extends HashMap<K, V>
        implements Serializable {

    private final long timeToLiveMs;

    private final Map<Object, Long> expirationHashMap = new HashMap<>();

    /**
     * Construct a map with a constant time-to-live value measured in milliseconds.
     * A negative time-to-live value will cause the entries to never expire.
     *
     * @param timeToLiveMs the constant amount of time (in milliseconds) an
     *                     entry is available before it expires. The entry is still stored
     *                     until an accessing method is called on the map.
     */
    public PassiveExpiringMap(final long timeToLiveMs) {
        this.timeToLiveMs = timeToLiveMs;
    }

    /**
     * Construct a map with a constant time-to-live value,
     * converted to milliseconds based on the provided time unit.
     * A negative time-to-live value will cause the entries to never expire.
     *
     * @param timeToLive the constant amount of time an
     *                   entry is available before it expires. The entry is still stored
     *                   until an accessing method is called on the map.
     * @param timeUnit   the unit of time for the <code>timeToLive</code>  parameter.
     */
    public PassiveExpiringMap(final long timeToLive, final TimeUnit timeUnit) {
        this.timeToLiveMs = TimeUnit.MILLISECONDS.convert(timeToLive, timeUnit);
    }

    /**
     * Updates expiration time of entry being checked, then
     * Expired entries are removed before checking if the map includes the key.
     * {@inheritDoc}
     */
    @Override
    public boolean containsKey(Object key) {
        updateExpirationTime(key);
        removeExpiredEntries();
        return super.containsKey(key);
    }

    /**
     * Expired entries are removed before checking if the map contains the value.
     * {@inheritDoc}
     */
    @Override
    public boolean containsValue(Object value) {
        removeExpiredEntries();
        return super.containsValue(value);
    }

    /**
     * Expired entries are removed before returning the entry set.
     * {@inheritDoc}
     */
    @Override
    public Set<Entry<K, V>> entrySet() {
        removeExpiredEntries();
        return super.entrySet();
    }

    /**
     * Updates expiration time of key being fetched, then
     * Expired entries are removed before attempting to retrieve the entry.
     * {@inheritDoc}
     */
    @Override
    public V get(Object key) {
        updateExpirationTime(key);
        removeExpiredEntries();
        return super.get(key);
    }

    /**
     * Expired entries are removed before returning the size.
     * {@inheritDoc}
     */
    @Override
    public int size() {
        removeExpiredEntries();
        return super.size();
    }

    /**
     * Expired entries are removed before determining emptiness.
     * {@inheritDoc}
     */
    @Override
    public boolean isEmpty() {
        removeExpiredEntries();
        return super.isEmpty();
    }

    /**
     * Expired entries are removed before returning the key set.
     * {@inheritDoc}
     */
    @Override
    public Set<K> keySet() {
        removeExpiredEntries();
        return super.keySet();
    }

    /**
     * Expired entries are removed before inserting the entry.
     * Inserts the expiration time of the given entry into the expiration map.
     * {@inheritDoc}
     */
    @Override
    public V put(K key, V value) {
        removeExpiredEntries();

        long expirationTime = calculateExpirationTime();
        expirationHashMap.put(key, Long.valueOf(expirationTime));

        return super.put(key, value);
    }

    /**
     * Expired entries are removed before attempting to remove the entry.
     * Removes the entry from the expiration map.
     * {@inheritDoc}
     */
    @Override
    public V remove(Object key) {
        removeExpiredEntries();
        expirationHashMap.remove(key);
        return super.remove(key);
    }

    /**
     * Expired entries are removed before returning the values.
     * {@inheritDoc}
     */
    @Override
    public Collection<V> values() {
        removeExpiredEntries();
        return super.values();
    }

    /**
     * Checks if the given expiration time has already passed.
     * If the current time is greater than or equal to the given time, returns true;
     *
     * @param expiration The Long object representing the expiration time
     */
    private boolean isExpired(Long expiration) {
        if (expiration == null) {
            return false;
        }

        long now = System.currentTimeMillis();
        long expirationTime = expiration.longValue();

        return expirationTime >= 0 && now >= expirationTime;
    }

    /**
     * Removes all entries in the Map which are expired.
     */
    private void removeExpiredEntries() {
        Iterator<Entry<Object, Long>> iterator = expirationHashMap.entrySet().iterator();

        while (iterator.hasNext()) {

            Map.Entry<Object, Long> expirationEntry = iterator.next();
            if (isExpired(expirationEntry.getValue())) {
                super.remove(expirationEntry.getKey());
                iterator.remove();
            }
        }
    }

    /**
     * Calculates the time of expiry in milliseconds based on the current time and time-to-live value.
     */
    private long calculateExpirationTime() {
        boolean timeToLiveIsNegative = timeToLiveMs < 0L;
        long now = System.currentTimeMillis();
        boolean expirationWouldBeGreaterThanMAX = now > Long.MAX_VALUE - timeToLiveMs;

        if (timeToLiveIsNegative || expirationWouldBeGreaterThanMAX) {
            // Never expire
            return -1L;
        }

        return now + timeToLiveMs;
    }

    /**
     * Updates the expiration time for a given key.
     *
     * @param key The key whose expiry time is updated.
     */
    private void updateExpirationTime(Object key) {
        expirationHashMap.put(key, calculateExpirationTime());
    }

}
