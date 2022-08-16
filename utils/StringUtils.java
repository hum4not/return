package org.returnclient.utils;

import java.util.Random;

public class StringUtils {

    public static String getRandomString(int max) {
        int leftMax = 97;
        int rightMax = 122;
        int StringMaxLength = max;
        Random randoms = new Random();

        String random = randoms.ints(leftMax, rightMax + 1).limit(StringMaxLength).collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString().toUpperCase();

        return random;
    }
}
