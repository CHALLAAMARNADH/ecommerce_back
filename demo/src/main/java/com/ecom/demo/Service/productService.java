package com.ecom.demo.Service;

import com.ecom.demo.Model.Product;
import com.ecom.demo.Repository.productRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class productService {
    @Autowired
    private productRepo repo;
    public List<Product> getallproducts()
    {

        return repo.findAll();
    }
    public Product getproduct(int id)
    {

        return repo.findById(id).orElse(null);
    }

    public Product addproduct(Product product, MultipartFile imageFile) throws IOException {
        if (imageFile != null && !imageFile.isEmpty()) {
            product.setImageName(imageFile.getOriginalFilename());
            product.setImageType(imageFile.getContentType());
            product.setImageDate(imageFile.getBytes());  // store image as byte[]
        }
        return repo.save(product);
    }

    public Product getProductById(int id) {
        return repo.findById(id).orElse(null);
    }
    public Product updateProduct(int id, Product prod, MultipartFile imageFile) throws IOException {
        return repo.findById(id).map(existing -> {
            // update fields
            existing.setName(prod.getName());
            existing.setBrand(prod.getBrand());
            existing.setPrice(prod.getPrice());
            existing.setDescription(prod.getDescription());
            existing.setStockQuantity(prod.getStockQuantity());
            existing.setCategory(prod.getCategory());
            existing.setReleaseDate(prod.getReleaseDate());

            // update image only if new one uploaded
            if (imageFile != null && !imageFile.isEmpty()) {
                try {
                    existing.setImageDate(imageFile.getBytes());
                    existing.setImageName(imageFile.getOriginalFilename());
                    existing.setImageType(imageFile.getContentType());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            return repo.save(existing);
        }).orElse(null);
    }

    public void deleteProduct(int id) {
        repo.deleteById(id);
    }
    public void reduceStock(int productId, int quantity) {
        repo.findById(productId).ifPresent(product -> {
            int newStock = product.getStockQuantity() - quantity;
            product.setStockQuantity(Math.max(newStock, 0));
            repo.save(product);
        });
    }



}
