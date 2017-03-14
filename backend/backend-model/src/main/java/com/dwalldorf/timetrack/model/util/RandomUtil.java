package com.dwalldorf.timetrack.model.util;

import java.util.Random;
import org.apache.commons.math3.random.RandomDataGenerator;
import org.springframework.stereotype.Component;

@Component
public class RandomUtil {

    @SuppressWarnings("SpellCheckingInspection")
    private static final char[] CHARS = "abcdefghijklmnopqrstuvwxyz".toCharArray();

    private static final char[] MONGO_ID_CHARS = "0123456789abcdef".toCharArray();

    private final Random random;

    private final RandomDataGenerator dataGenerator;

    public RandomUtil() {
        this.random = new Random();
        this.dataGenerator = new RandomDataGenerator();
    }

    public boolean randomBoolean() {
        return random.nextBoolean();
    }

    public String randomString(final int length) {
        return randomString(CHARS, length);
    }

    public String mongoId() {
        return randomString(MONGO_ID_CHARS, 24);
    }

    private String randomString(final char[] chars, final int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
        return sb.toString();
    }

    public int randomInt(final int min, final int max) {
        return dataGenerator.nextInt(min, max);
    }

    public int randomInt(final int max) {
        return dataGenerator.nextInt(0, max);
    }

    public long randomLong(final long min, final long max) {
        return dataGenerator.nextLong(min, max);
    }

    public long randomLong(final long max) {
        return this.randomLong(0, max);
    }
}