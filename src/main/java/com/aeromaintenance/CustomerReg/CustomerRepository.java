package com.aeromaintenance.CustomerReg;


import com.aeromaintenance.CustomerReg.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("SELECT c.customerName FROM Customer c")
    List<String> findAllCustomerNames();
}
