package com.aeromaintenance.product;

import com.common.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/product")
//@CrossOrigin(origins = "http://localhost:5173")
public class ProductController {

    @Autowired
    private ProductService productService;
    
    public ProductController() {
        System.out.println("ProductController Initialized");
    }

    // Create Product
    @PostMapping("/create")
    public ResponseEntity<String> createProduct(@RequestBody Product product) {
        System.out.println("Creating product: " + product);
        productService.saveProduct(product);
        return ResponseEntity.ok("Product created successfully");
    }


    // Get All Products
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }
   
	// Get Product by ID

	@GetMapping("/{id}")
	public ResponseEntity<Product> getProductById(@PathVariable Long id) {
		Product product = productService.getProductById(id);
		if (product != null) {
			return ResponseEntity.ok(product);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
    
 // Delete Product by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        if (product != null) {
            productService.deleteProductById(id);
            return ResponseEntity.ok("Product deleted successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    // Update Product by ID
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProductById(@PathVariable Long id, @RequestBody Product updatedProduct) {
        Product product = productService.updateProduct(id, updatedProduct);
        if (product != null) {
            return ResponseEntity.ok(product);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Fetch Product Name and Product Desc
    @GetMapping("/prodNameDesc")
    public ResponseEntity<List<ProductDTO>> getPartNumPartDesc(){
        return ResponseEntity.ok(productService.getProdNumProdDesc());
    }

    @DeleteMapping("/{id}/soft-delete")
    public ResponseEntity<String> softDeleteProduct(@PathVariable Long id) {
        Optional<Product> optionalProduct = Optional.ofNullable(productService.getProductById(id));
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            product.setFlag(0);  // soft delete
            productService.saveProduct(product);
            return ResponseEntity.ok("Product soft deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
        }
    }

    @GetMapping("/active")
    public List<Product> getActiveProducts() {
        return productService.getActiveProduct();
    }



}
