package com.aeromaintenance.product;

import org.springframework.stereotype.Service;


import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // Save Product
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    // Get All Products
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Get Product by ID
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }
    
    // Delete Product by ID
    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }
    
 // Update Product
    public Product updateProduct(Long id, Product updatedProduct) {
        return productRepository.findById(id)
            .map(existingProduct -> {
                // Update all necessary fields
                existingProduct.setProductName(updatedProduct.getProductName());
                existingProduct.setProductDescription(updatedProduct.getProductDescription());
                existingProduct.setUnitOfMeasurement(updatedProduct.getUnitOfMeasurement());
                existingProduct.setOem(updatedProduct.getOem());
                existingProduct.setNha(updatedProduct.getNha());
                existingProduct.setCmmReferenceNumber(updatedProduct.getCmmReferenceNumber());
//                existingProduct.setCmmRef2(updatedProduct.getCmmRef2());
//                existingProduct.setCmmRef3(updatedProduct.getCmmRef3());
                existingProduct.setMaterialClassification(updatedProduct.getMaterialClassification());
                existingProduct.setRegisteredBy(updatedProduct.getRegisteredBy());
                return productRepository.save(existingProduct);
            })
            .orElse(null);
    }

}
