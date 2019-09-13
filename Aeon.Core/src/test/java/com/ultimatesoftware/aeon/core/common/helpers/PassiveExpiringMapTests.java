package com.ultimatesoftware.aeon.core.common.helpers;

import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class PassiveExpiringMapTests {


    private Map<String, Integer> buildTestMap(long timeToLiveMs) {
        Map<String, Integer> map = new PassiveExpiringMap<>(timeToLiveMs);
        map.put("First", 1);
        map.put("Second", 2);
        map.put("Third", 3);
        map.put("Fourth", 4);
        map.put("Fifth", 5);
        return map;
    }

    private Map<String, Integer> buildTestMap(long timeToLive, TimeUnit timeUnit) {
        Map<String, Integer> map = new PassiveExpiringMap<>(timeToLive, timeUnit);
        map.put("First", 1);
        map.put("Second", 2);
        map.put("Third", 3);
        map.put("Fourth", 4);
        map.put("Fifth", 5);
        return map;
    }

    private Map<String, Integer> buildTestMap(long timeToLiveMs, long sleepInterval) {
        Map<String, Integer> map = new PassiveExpiringMap<>(timeToLiveMs);
        map.put("First", 1);
        map.put("Second", 2);
        map.put("Third", 3);

        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            fail();
        }

        map.put("Fourth", 4);
        map.put("Fifth", 5);
        map.put("Sixth", 6);
        return map;
    }

    @Test
    public void constructMap_negativeTimeToLive_noExpiration() {
        // Arrange

        // Act
        Map<String, Integer> map = buildTestMap(-1);

        // Assert
        assertEquals(5, map.size());
    }

    @Test
    public void constructMap_zeroTimeToLive_expireImmediately() {
        // Arrange

        // Act
        Map<String, Integer> map = buildTestMap(0);

        // Assert
        assertEquals(0, map.size());
    }

    @Test
    public void constructMap_withTimeUnit() {
        // Arrange

        // Act
        Map<String, Integer> map = buildTestMap(1, TimeUnit.HOURS);

        // Assert
        assertEquals(5, map.size());
    }

    @Test
    public void putEntry_entriesExpired() {
        // Arrange
        Map<String, Integer> map = buildTestMap(100, 101);

        // Act
        map.put("First", 1);

        // Assert
        assertEquals(4, map.size());
    }

    @Test
    public void testCollections() {
        // Arrange
        Map<String, Integer> map = buildTestMap(200, 201);

        // Act
        int size = map.size();
        int keysetSize = map.keySet().size();
        int valuesSize = map.values().size();
        int entrySetSize = map.values().size();

        // Assert
        assertEquals(3, size);
        assertEquals(3, keysetSize);
        assertEquals(3, valuesSize);
        assertEquals(3, entrySetSize);
    }

    @Test
    public void testGet() {
        // Arrange
        Map<String, Integer> map = buildTestMap(1, TimeUnit.HOURS);

        // Act
        int value1 = map.get("First");
        int value2 = map.get("Fifth");

        // Assert
        assertEquals(1, value1);
        assertEquals(5, value2);
    }

    @Test
    public void testGet_updatesExpiration() {
        // Arrange
        Map<String, Integer> map = buildTestMap(200);

        // Act
        try {
            Thread.sleep(100);
            map.get("First");
            Thread.sleep(100);
        } catch (InterruptedException e) {
            fail();
        }

        // Assert
        assertEquals(1, map.size());
        assertNotNull(map.get("First"));
    }

    @Test
    public void testRemove() {
        // Arrange
        Map<String, Integer> map = buildTestMap(1, TimeUnit.HOURS);

        // Act
        int value1 = map.remove("First");
        int value2 = map.remove("Fifth");

        // Assert
        assertEquals(1, value1);
        assertEquals(5, value2);
        assertEquals(3, map.size());
    }

    @Test
    public void testPut() {
        // Arrange
        Map<String, Integer> map = buildTestMap(1, TimeUnit.HOURS);

        // Act
        map.put("Fifth", 5000);
        map.put("Sixth", 6);

        // Assert
        assertEquals(6, map.size());
    }

    @Test
    public void testIsEmpty() {
        // Arrange
        Map<String, Integer> map = buildTestMap(1, TimeUnit.HOURS);

        // Act
        boolean emptyBeforeRemove = map.isEmpty();
        map.remove("First");
        map.remove("Second");
        map.remove("Third");
        map.remove("Fourth");
        map.remove("Fifth");
        boolean emptyAfterRemove = map.isEmpty();

        // Assert
        assertFalse(emptyBeforeRemove);
        assertTrue(emptyAfterRemove);
    }


}
