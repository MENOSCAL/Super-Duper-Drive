package com.udacity.jwdnd.course1.cloudstorage.utils;

public class AppConstant {
    public static final Long MAX_SIZE_FILE = convertMegabyteToByte(2000L);

    private static Long convertMegabyteToByte(Long Megabyte) {
        return Megabyte * 1_024 * 1_024;
    }
}
