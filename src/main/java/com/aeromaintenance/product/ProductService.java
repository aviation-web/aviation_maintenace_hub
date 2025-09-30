package com.aeromaintenance.product;

import com.common.ProductDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // Save Product
    public Product saveProduct(Product product) {
        // Collect all product names into a stream
        List<String> productNames = Stream.of(
                        product.getProductName(),
                        product.getAlternateProduct1(),
                        product.getAlternateProduct2()
                )
                .filter(Objects::nonNull)                // Remove nulls
                .map(String::trim)                       // Trim each value
                .filter(name -> !name.isEmpty())         // Remove empty strings
                .collect(Collectors.toList());

        // Check if any of the names already exist
        for (String name : productNames) {
            if (productRepository.existsByProductName(name)) {
                throw new ResponseStatusException(HttpStatus.CONFLICT,
                        "Part number already exists: " + name);
            }
        }

        // Save the main product first
        product.setProductName(product.getProductName().trim());
        String mappingType = product.getMappingType();
        String productName = product.getProductName();

        String mapping = mappingType == null ? "" : mappingType.toUpperCase();

        if ("UP".equals(mapping)) {
            product.setAlternateProduct1(null);
            product.setAlternateProduct2(null);
        } else {
            product.setAlternateProduct1(product.getAlternateProduct1());
            product.setAlternateProduct2(product.getAlternateProduct2());
        }

        productRepository.save(product);

        productNames.stream()
                .skip(1) // Skip main product name (already saved)
                .forEach(name -> {
                    Product alt = cloneProduct(product, name);
                    productRepository.save(alt);
                });

        return product;
    }

//    public Product saveProduct(Product product) {
//        // If product has an ID, it’s an update
//        if (product.getProductId() == null) {
//            // Create case → check duplicate
//            if (productRepository.existsByProductName(product.getProductName())) {
//                throw new ResponseStatusException(HttpStatus.CONFLICT, "Part number already exists");
//            }
//        } else {
//            // Update case → check only if name belongs to another product
//            boolean existing = productRepository.existsByProductName(product.getProductName());
//            if (!existing) {
//                throw new ResponseStatusException(HttpStatus.CONFLICT, "Part number already exists");
//            }
//        }
//
//        return productRepository.save(product);
//    }


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
        if (productRepository.existsByProductName(updatedProduct.getProductName())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Part number already exists");
        }

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
                existingProduct.setRegistrationDate(updatedProduct.getRegistrationDate());
                existingProduct.setMaterialClassification(updatedProduct.getMaterialClassification());
                existingProduct.setRegisteredBy(updatedProduct.getRegisteredBy());
                existingProduct.setAlternateProduct1(updatedProduct.getAlternateProduct1());
                existingProduct.setAlternateProduct2(updatedProduct.getAlternateProduct2());
                existingProduct.setMappingType(updatedProduct.getMappingType());
                return productRepository.save(existingProduct);
            })
            .orElse(null);
    }

    public List<ProductDTO> getProdNumProdDesc(){
        return productRepository.findAllProductNameAndDescriptionDTO();
    }

    public List<ProductDTO> searchProducts(String searchTerm) {
        List<ProductDTO> allProducts = getProdNumProdDesc();
        List<ProductDTO> results = new ArrayList<>();

        for (ProductDTO p : allProducts) {
            String mt = p.getMappingType();
            boolean match = false;

            if ("UP".equalsIgnoreCase(mt)) {
                // Alternate → Product only
                if (searchTerm.equalsIgnoreCase(p.getAlternateProduct1()) ||
                        searchTerm.equalsIgnoreCase(p.getAlternateProduct2())) {
                    match = true;
                }
            } else if ("DOWN".equalsIgnoreCase(mt)) {
                // Product → Alternate only
                if (searchTerm.equalsIgnoreCase(p.getProductName())) {
                    match = true;
                }
            } else if ("BOTH".equalsIgnoreCase(mt)) {
                // Both ways
                if (searchTerm.equalsIgnoreCase(p.getProductName()) ||
                        searchTerm.equalsIgnoreCase(p.getAlternateProduct1()) ||
                        searchTerm.equalsIgnoreCase(p.getAlternateProduct2())) {
                    match = true;
                }
            }

            if (match) {
                results.add(p);
            }
        }

        return results;
    }

    public List<Product> getActiveProduct(){
        return productRepository.findAllActiveProducts();
    }


    private Product cloneProduct(Product source, String newProductName) {
        Product copy = new Product();
        BeanUtils.copyProperties(source, copy);
        copy.setProductId(null);
        copy.setProductName(newProductName);
        String mappingType = source.getMappingType();

        if ("DOWN".equals(mappingType)) {
            copy.setAlternateProduct1(null);
            copy.setAlternateProduct2(null);
        } else {
            copy.setAlternateProduct1(source.getProductName());
            copy.setAlternateProduct2(source.getProductName());
          }
        return copy;
    }

	public void updateFlag(Product product) {
		productRepository.save(product);
		
	}
}
