package br.ufms.facom.home.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class Utils {

    public static String encrypt(String value) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedhash = digest.digest(
                    value.getBytes(StandardCharsets.UTF_8));
            return new String(encodedhash);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
