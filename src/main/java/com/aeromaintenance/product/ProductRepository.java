package com.aeromaintenance.product;

import com.common.ProductDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT new com.common.ProductDTO(p.productName, p.productDescription) FROM Product p")
    List<ProductDTO> findAllProductNameAndDescriptionDTO();

    @Query("SELECT p FROM Product p WHERE p.flag = 1")
    List<Product> findAllActiveProducts();

}

