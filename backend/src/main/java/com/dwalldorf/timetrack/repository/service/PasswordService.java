package com.dwalldorf.timetrack.repository.service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Random;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import org.springframework.stereotype.Service;

@Service
public class PasswordService {

    private final static String ALGORITHM = "PBKDF2WithHmacSHA256";
    private final static int ITERATIONS = 10000;
    private final static int KEY_LENGTH = 256;

    private final SecretKeyFactory keyFactory;

    public PasswordService() throws NoSuchAlgorithmException {
        keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
    }


    public byte[] createSalt() {
        final Random r = new SecureRandom();
        byte[] salt = new byte[32];
        r.nextBytes(salt);
        return salt;
    }

    public byte[] hash(char[] password, byte[] salt) {
        PBEKeySpec keySpec = new PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH);
        Arrays.fill(password, Character.MIN_VALUE);
        try {
            return keyFactory.generateSecret(keySpec).getEncoded();
        } catch (InvalidKeySpecException e) {
            throw new AssertionError("Error while hashing a password: " + e.getMessage(), e);
        } finally {
            keySpec.clearPassword();
        }
    }

    public boolean isExpectedPassword(char[] password, byte[] salt, byte[] expectedHash) {
        byte[] pwdHash = hash(password, salt);

        Arrays.fill(password, Character.MIN_VALUE);
        if (pwdHash.length != expectedHash.length) {
            return false;
        }
        for (int i = 0; i < pwdHash.length; i++) {
            if (pwdHash[i] != expectedHash[i]) {
                return false;
            }
        }
        return true;
    }
}
