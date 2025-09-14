package com.aeromaintenance.CustomerReg;


import com.aeromaintenance.CustomerReg.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
