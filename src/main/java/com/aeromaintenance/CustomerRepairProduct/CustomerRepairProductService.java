package com.aeromaintenance.CustomerRepairProduct;


import com.aeromaintenance.CustomerRepairProduct.CustomerRepairProduct;
import com.aeromaintenance.CustomerRepairProduct.CustomerRepairProductRepository;
import com.common.CustomerRepairDTO;
import com.common.ProductDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerRepairProductService {

    private final CustomerRepairProductRepository repository;

    public CustomerRepairProductService(CustomerRepairProductRepository repository) {
        this.repository = repository;
    }

    public CustomerRepairProduct save(CustomerRepairProduct product) {
        product.setDate(LocalDate.now()); // set system date
        return repository.save(product);
    }

    public List<CustomerRepairProduct> findAll() {
        return repository.findAll();
    }

    public Optional<CustomerRepairProduct> findById(Long id) {
        return repository.findById(id);
    }

    public CustomerRepairProduct update(Long id, CustomerRepairProduct product) {
        return repository.findById(id).map(existing -> {
            existing.setProductName(product.getProductName());
            existing.setProductSerialNumbers(product.getProductSerialNumbers());
            existing.setProductDescription(product.getProductDescription());
            existing.setUnitOfMeasurement(product.getUnitOfMeasurement());
            existing.setOem(product.getOem());
            existing.setCmmRefNo(product.getCmmRefNo());
            existing.setRegisterBy(product.getRegisterBy());
            return repository.save(existing);
        }).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public List<CustomerRepairDTO> getProdNumProdDescByName(String productName) {
        List<CustomerRepairProduct> products = repository.findByProductName(productName);
        return products.stream()
                .map(p -> new CustomerRepairDTO(
                        p.getProductName(),
                        p.getProductDescription(),
                        p.getUnitOfMeasurement(),
                        p.getProductSerialNumbers() // now in memory, fine
                ))
                .collect(Collectors.toList());
    }

    public List<CustomerRepairDTO> getAllProdNames() {
        return repository.findAll().stream()
                .map(p -> new CustomerRepairDTO(p.getProductName(),  null,                 // baki fields null
                        null,
                        null))
                .collect(Collectors.toList());
    }

}
