package com.example.scheduler.api.util;
import org.apache.commons.codec.binary.Base64;
import java.util.UUID;
public class StringUtils {

    public static boolean isInteger(String val) {
        try {
            Integer.valueOf(val);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static String createBase64EncodedUUID() {
        UUID uuid = UUID.randomUUID();
        byte[] bytesUuid = new byte[16];
        for (int i = 0; i < 8; i++) {
            bytesUuid[i] = (byte) ((uuid.getMostSignificantBits() >> (8 * i)) & 0xff);
        }
        for (int i = 8; i < 16; i++) {
            bytesUuid[i] = (byte) ((uuid.getLeastSignificantBits() >> (8 * i)) & 0xff);
        }
        return Base64.encodeBase64URLSafeString(bytesUuid).trim();
    }
}
