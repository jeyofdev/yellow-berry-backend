package com.jeyofdev.yellow_berry.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Helper {
    /**
     * Format date to yyyy-MM-dd HH:mm:ss
     *
     * @return String
     */
    public static String simpleDateFormat() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(new Date());
    }
}
