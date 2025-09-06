package com.titan.milap;
import android.text.format.DateUtils;

public class TimeFormatter {

    public static String getTimeAgo(long timeMillis) {
        long now = System.currentTimeMillis();
        if (timeMillis > now || timeMillis <= 0) {
            return "just now";
        }

        final long diff = now - timeMillis;

        if (diff < DateUtils.MINUTE_IN_MILLIS) {
            return "just now";
        } else if (diff < DateUtils.HOUR_IN_MILLIS) {
            long minutes = diff / DateUtils.MINUTE_IN_MILLIS;
            return minutes + " min";
        } else if (diff < DateUtils.DAY_IN_MILLIS) {
            long hours = diff / DateUtils.HOUR_IN_MILLIS;
            return hours + " hr" + (hours > 1 ? "s" : "");
        } else {
            long days = diff / DateUtils.DAY_IN_MILLIS;
            return days + " day" + (days > 1 ? "s" : "");
        }
    }
}

