package com.shortenme.urlShortener.Utils;

public abstract class BaseConverterUtil {

    private static final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    public static String base10To62(Long base10) {
        StringBuilder sb = new StringBuilder();
        do {
            int rem = (int) (base10 % 62);
            sb.append(ALPHABET.charAt(rem));
            base10 /= 62;
        } while (base10 > 0);
        return sb.reverse().toString();
    }


    public static long convertToBase10(String base62) {
        long result = 0;
        int base = ALPHABET.length();

        for (char character : base62.toCharArray()) {
            result = result * base + ALPHABET.indexOf(character);
        }
        return result;
    }
}
