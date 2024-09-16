package com;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class PasswordUtils {

    private static final Logger LOG = Logger.getLogger(PasswordUtils.class.getName());

    private PasswordUtils() {
        throw new UnsupportedOperationException("This is an utility class and should be instantiated");

    }

    public static boolean verifyPassword(String password, String storedHash, String storedSalt) {
        int iterations = 65536;
        int keyLength = 256;

        try {
            byte[] salt = Base64.getDecoder().decode(storedSalt);
            PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterations, keyLength);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            byte[] hash = factory.generateSecret(spec).getEncoded();

            String computedHash = Base64.getEncoder().encodeToString(hash);

            return storedHash.equals(computedHash);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Error verifying password", e);
            return false;
        }
    }

    public static String[] encodePassword(String password) {

        byte[] salt = generateSalt();
        int iterations = 65536;
        int keyLength = 256;
        String encodedHash = "";

        try {
            PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterations, keyLength);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            byte[] hash = factory.generateSecret(spec).getEncoded();

            encodedHash = Base64.getEncoder().encodeToString(hash);
            String encodedSalt = Base64.getEncoder().encodeToString(salt);

            return new String[] { encodedHash, encodedSalt };
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Error generating the hash", e);
        }
        return new String[] { "", "" };
    }

    private static byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

}
