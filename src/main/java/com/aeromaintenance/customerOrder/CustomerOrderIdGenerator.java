package com.aeromaintenance.customerOrder;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

public class CustomerOrderIdGenerator implements IdentifierGenerator {

    private static final AtomicInteger counter = new AtomicInteger(0);

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) {
        int id = counter.incrementAndGet();
        return String.format("CUST%03d", id);  // Ex: CUST001, CUST002 ...
    }
}
