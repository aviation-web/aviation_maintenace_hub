package com.aeromaintenance.Config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class LocationBasedRoutingDataSourceContextHolder {
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();


    public static void setLocation(String location) {
        contextHolder.set(location);
    }

    public static String getLocation() {
        return contextHolder.get();
    }

    public static void clear() {
        contextHolder.remove();
    }
}
