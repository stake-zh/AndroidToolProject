package com.example.starkey.androidtoolproject.untils;

public class SafeNumberParser {
    public static long parseLong(String str) {
        return parseLong(str, 10);
    }

    public static long parseLong(String str, int radix) {
        try {
            return Long.parseLong(str, radix);
        } catch (Throwable e) {
            e.printStackTrace();
            return 0L;
        }
    }

    public static int parseInt(String str) {
        return parseInt(str, 10);
    }

    public static int parseInt(String str, int radix) {
        try {
            return Integer.parseInt(str, radix);
        } catch (Throwable e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static long longValueOf(String str) {
        return longValueOf(str, 10);
    }

    public static long longValueOf(String str, int radix) {
        try {
            return Long.valueOf(str, radix);
        } catch (Throwable e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static int intValueOf(String str) {
        return intValueOf(str, 10);
    }

    public static int intValueOf(String str, int radix) {
        try {
            return Integer.valueOf(str, radix);
        } catch (Throwable e) {
            e.printStackTrace();
            return 0;
        }
    }
}
