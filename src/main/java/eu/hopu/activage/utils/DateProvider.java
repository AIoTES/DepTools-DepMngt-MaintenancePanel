package eu.hopu.activage.utils;

import java.util.Date;

public class DateProvider {

    private static DateProvider instance;

    public DateProvider() {
    }

    public static DateProvider getInstance() {
        if (instance == null)
            instance = new DateProvider();
        return instance;
    }

    public static void _setInstance(DateProvider dateProvider) {
        instance = dateProvider;
    }

    public Date getDate() {
        return new Date();
    }
}
