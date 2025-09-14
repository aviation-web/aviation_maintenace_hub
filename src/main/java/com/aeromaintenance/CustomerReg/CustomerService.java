package com.aeromaintenance.CustomerReg;


import com.aeromaintenance.CustomerReg.Customer;
import com.aeromaintenance.CustomerReg.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Optional<Customer> getCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    public Customer updateCustomer(Long id, Customer updatedCustomer) {
        return customerRepository.findById(id).map(customer -> {
            customer.setCustomerName(updatedCustomer.getCustomerName());
            customer.setContactPersonName(updatedCustomer.getContactPersonName());
            customer.setPhoneNo(updatedCustomer.getPhoneNo());
            customer.setMobileNumber(updatedCustomer.getMobileNumber());
            customer.setEmailId(updatedCustomer.getEmailId());
            customer.setShipToAddress1(updatedCustomer.getShipToAddress1());
            customer.setShipToAddress2(updatedCustomer.getShipToAddress2());
            customer.setShipToAddress3(updatedCustomer.getShipToAddress3());
            customer.setBillToAddress(updatedCustomer.getBillToAddress());
            customer.setPaymentTerms(updatedCustomer.getPaymentTerms());
            customer.setGstNo(updatedCustomer.getGstNo());
            customer.setCustomerType(updatedCustomer.getCustomerType());
            return customerRepository.save(customer);
        }).orElseThrow(() -> new RuntimeException("Customer not found with id " + id));
    }

    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }
}
