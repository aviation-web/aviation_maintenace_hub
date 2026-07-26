package com.aeromaintenance.customerOrder;

import java.io.Serializable;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import javax.persistence.Query;

public class CustomerOrderIdGenerator implements IdentifierGenerator {

    private static final String PREFIX = "CUST";

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) {
        try {
            // Last sr_no fetch kara
            Query query = session.createNativeQuery("SELECT sr_no FROM customer_order ORDER BY sr_no DESC LIMIT 1");
            String lastId = (String) query.getSingleResult();

            int nextId = 1;
            if (lastId != null && lastId.startsWith(PREFIX)) {
                nextId = Integer.parseInt(lastId.substring(PREFIX.length())) + 1;
            }

            // Format → CUST001, CUST002, ...
            return PREFIX + String.format("%03d", nextId);

        } catch (Exception e) {
            return PREFIX + "001"; // First record
        }
    }
}
