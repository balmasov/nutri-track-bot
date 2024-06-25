package com.nutritrack.bot.util;

public class NumberExtractHelper {
    private static final String NON_DIGIT = "\\D+";
    private static final String ONLY_DIGITS = "\\d+";

    public static boolean onlyDigits(String str) {
        return !str.isEmpty() && str.matches(ONLY_DIGITS);
    }

    public static String extractDigitsOrEmptyStr(String userMsg) {
        return userMsg.replaceAll(NON_DIGIT, "");
    }

    public static Long extractLong(String userMsg) {
        return Long.parseLong(userMsg.replaceAll(NON_DIGIT, ""));
    }
}
