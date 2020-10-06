package com.fitbitsample.util;

import android.util.Base64;

import java.security.MessageDigest;
import java.security.SecureRandom;

public class CryptoUtil {
    public static String getCodeVerifier() {
        SecureRandom sr = new SecureRandom();
        byte[] code = new byte[32];
        sr.nextBytes(code);
        return Base64.encodeToString(code, Base64.URL_SAFE | Base64.NO_WRAP | Base64.NO_PADDING);
    }

    public static String getCodeChallenge(String verifier) {
        try {
            byte[] bytes = verifier.getBytes("US-ASCII");
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(bytes, 0, bytes.length);
            byte[] digest = md.digest();
//Use Apache "Commons Codec" dependency. Import the Base64 class
//import org.apache.commons.codec.binary.Base64;
            return Base64.encodeToString(digest, Base64.DEFAULT);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }


    public static String getEncodedValue(String text) {
        try {
            byte[] data = text.getBytes("UTF-8");
            return Base64.encodeToString(data, Base64.NO_WRAP);
        } catch (Exception ex) {
            return null;
        }
    }
}
