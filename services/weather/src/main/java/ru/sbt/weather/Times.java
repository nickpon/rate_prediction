package ru.sbt.weather;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Times {
    private static Calendar initTime() {
        Calendar initTime = new GregorianCalendar();
        initTime.add(Calendar.DAY_OF_MONTH, -1);
        initTime.set(Calendar.HOUR_OF_DAY, 0);
        initTime.set(Calendar.MINUTE, 0);
        initTime.set(Calendar.SECOND, 0);
        return initTime;
    }

    private static void previousDay(Calendar day) {
        day.add(Calendar.DAY_OF_MONTH, -1);
    }

    private static Long day2UNIXTime(Calendar day) {
        return day.getTime().getTime() / 1000;
    }

    static ArrayList<Long> getUNIXTimes(int period) {
        ArrayList<Long> times = new ArrayList<>(period);
        Calendar currentTime = initTime();
        for (int i = 0; i < period; i++) {
            times.add(day2UNIXTime(currentTime));
            previousDay(currentTime);
        }
        return times;
    }
}
