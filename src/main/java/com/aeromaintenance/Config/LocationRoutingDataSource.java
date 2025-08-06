package com.aeromaintenance.Config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class LocationRoutingDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        return LocationBasedRoutingDataSourceContextHolder.getLocation();
    }
}