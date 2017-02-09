package com.dwalldorf.timetrack.model.util;

import java.util.Random;
import org.apache.commons.math3.random.RandomDataGenerator;
import org.springframework.stereotype.Component;

@Component
public class RandomUtil {

    private final static char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();

    private final Random random;

    private final RandomDataGenerator dataGenerator;

    public RandomUtil() {
        this.random = new Random();
        this.dataGenerator = new RandomDataGenerator();
    }

    public boolean randomBoolean() {
        return random.nextBoolean();
    }

    public String randomString(int length) {
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