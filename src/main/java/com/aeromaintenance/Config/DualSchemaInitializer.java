package com.aeromaintenance.Config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;

@Component
public class DualSchemaInitializer {

    private final EntityManagerFactory mumbaiEntityManagerFactory;
    private final EntityManagerFactory delhiEntityManagerFactory;

    public DualSchemaInitializer(
        @Qualifier("mumbaiEntityManagerFactory") EntityManagerFactory mumbaiEntityManagerFactory,
        @Qualifier("delhiEntityManagerFactory") EntityManagerFactory delhiEntityManagerFactory
    ) {
        this.mumbaiEntityManagerFactory = mumbaiEntityManagerFactory;
        this.delhiEntityManagerFactory = delhiEntityManagerFactory;
    }

    @PostConstruct
    public void init() {
        // This will trigger schema creation/update on both DBs
        mumbaiEntityManagerFactory.createEntityManager().close();
        delhiEntityManagerFactory.createEntityManager().close();
    }
}
