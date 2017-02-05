package com.dwalldorf.timetrack.service;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.dwalldorf.timetrack.BaseTest;
import java.util.HashSet;
import java.util.Set;
import org.junit.Test;
import org.mockito.InjectMocks;

public class PasswordServiceTest extends BaseTest {

    private final static int ITERATIONS = 20;

    @InjectMocks
    private PasswordService passwordService;

    @Test
    public void testCreateSaltConstantLength() throws Exception {
        int saltLength = 0;
        for (int i = 0; i < ITERATIONS; i++) {
            byte[] salt = passwordService.createSalt();

            if (i == 0) {
                saltLength = salt.length;
            } else {
                assertEquals(saltLength, salt.length);
            }
        }
    }

    @Test
    public void testHashReturnsDistinctHashes() {
        final String PASSWORD = "test123";
        final byte[] SALT = passwordService.createSalt();

        Set<byte[]> hashes = new HashSet<>();
        for (int i = 0; i < ITERATIONS; i++) {
            byte[] hash = passwordService.hash(PASSWORD.toCharArray(), SALT);

            assertFalse(hashes.contains(hash));
            hashes.add(hash);
        }
    }

    @Test
    public void testIsExpectedPassword() {
        final String PASSWORD = "fsaHJSSAusdj12dDS*d7/dsa-DSpqsutvDIl";
        final byte[] SALT = passwordService.createSalt();

        Set<byte[]> hashes = new HashSet<>();
        for (int i = 0; i < ITERATIONS; i++) {
            byte[] hash = passwordService.hash(PASSWORD.toCharArray(), SALT);

            assertFalse(hashes.contains(hash));
            hashes.add(hash);
        }

        hashes.stream()
              .parallel()
              .forEach(hash -> assertTrue(passwordService.isExpectedPassword(PASSWORD.toCharArray(), SALT, hash)));
    }
}