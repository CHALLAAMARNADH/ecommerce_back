package com.ecom.demo.Controller;

import com.ecom.demo.Model.Product;
import com.ecom.demo.Service.productService;
import com.ecom.demo.dto.CartItemDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
public class productController {

    @Autowired
    private productService service;
     @GetMapping("/")
    public String home() {
        return "Backend is running!";
    }

    @GetMapping("/products")
    public List<Product> getAllProducts() {
        return service.getallproducts();
    }

    @GetMapping("/product/{id}")
    public Product getProduct(@PathVariable int id) {
        return service.getproduct(id); // will return null if not found
    }

    @PostMapping("/product")
    public Product addProduct(
            @RequestPart("product") Product product,
            @RequestPart("imageFile") MultipartFile imageFile
    ) throws Exception {
        return service.addproduct(product, imageFile);
    }

    @GetMapping("/product/{id}/image")
    public @ResponseBody byte[] getProductImage(@PathVariable int id) {
        Product product = service.getProductById(id);
        return product.getImageDate();  // raw bytes of image
    }

    @PutMapping("/product/{id}")
    public String updateProduct(@PathVariable int id,
                                @RequestPart("product") Product product,
                                @RequestPart(value = "image", required = false) MultipartFile imageFile) throws IOException {
        Product updated = service.updateProduct(id, product, imageFile);
        if (updated != null) {
            return "Updated";
        }
        return "Product not found";
    }

    @DeleteMapping("/product/{id}")
    public String delete(@PathVariable int id) {
        service.deleteProduct(id);
        return "Product deleted successfully";
    }

    @PostMapping("/checkout")
    public String checkout(@RequestBody List<CartItemDto> cartItems) {
        for (CartItemDto item : cartItems) {
            service.reduceStock(item.getId(), item.getQuantity());
        }
        return "Checkout successful";
    }
}
