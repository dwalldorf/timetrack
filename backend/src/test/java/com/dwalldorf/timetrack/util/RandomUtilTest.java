package com.dwalldorf.timetrack.util;

import static org.junit.Assert.assertTrue;

import com.dwalldorf.timetrack.BaseTest;
import java.util.HashSet;
import java.util.Set;
import org.junit.Test;
import org.mockito.InjectMocks;

public class RandomUtilTest extends BaseTest {

    @InjectMocks
    private RandomUtil randomUtil;

    private final int ITERATIONS = 1000;

    @Test
    public void testRandomBoolean() throws Exception {
        Set<Boolean> randomBooleans = new HashSet<>();
        for (int i = 0; i < ITERATIONS; i++) {
            randomBooleans.add(randomUtil.randomBoolean());
        }

        assertTrue(randomBooleans.size() == 2);
    }

    @Test
    public void testRandomString() throws Exception {
        Set<String> randomStrings = new HashSet<>();
        for (int i = 0; i < ITERATIONS; i++) {
            randomStrings.add(randomUtil.randomString(100));
        }

        assertTrue(randomStrings.size() == ITERATIONS);
    }

    @Test
    public void testRandomStringInBounds() throws Exception {
        final int expectedLength = 20;

        for (int i = 0; i < ITERATIONS; i++) {
            String currentString = randomUtil.randomString(expectedLength);
            assertTrue(currentString.length() == expectedLength);
        }
    }

    @Test
    public void testRandomInt() throws Exception {
        Set<Integer> randomInts = new HashSet<>();
        for (int i = 0; i < ITERATIONS; i++) {
            randomInts.add(randomUtil.randomInt(10000));
        }

        assertTrue(randomInts.size() >= (ITERATIONS / 2));
    }

    @Test
    public void testRandomIntInBounds() throws Exception {
        final int min = 1000;
        final int max = 1050;

        for (int i = 0; i < ITERATIONS; i++) {
            Integer currentInt = randomUtil.randomInt(min, max);

            assertTrue(currentInt >= min);
            assertTrue(currentInt <= max);
        }
    }

    @Test
    public void testRandomLong() throws Exception {
        Set<Long> randomLongs = new HashSet<>();
        for (int i = 0; i < ITERATIONS; i++) {
            randomLongs.add(randomUtil.randomLong(10000L));
        }

        assertTrue(randomLongs.size() >= (ITERATIONS / 2));
    }

    @Test
    public void testRandomLongInBounds() throws Exception {
        final long min = 1000L;
        final long max = 1050L;

        for (int i = 0; i < ITERATIONS; i++) {
            long currentLong = randomUtil.randomLong(min, max);

            assertTrue(currentLong >= min);
            assertTrue(currentLong <= max);
        }
    }
}