package com.rockstor.test.util;
import java.util.Random;

public class RsUtil {
    public static String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static Random rng = new Random();
    public static void init() throws Exception {
        try {

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
    public static String generateRandomString(int length)
    {
        char[] text = new char[length];
        for (int i = 0; i < length; i++)
        {
            text[i] = characters.charAt(rng.nextInt(characters.length()));
        }
        return new String(text);
    }

}
