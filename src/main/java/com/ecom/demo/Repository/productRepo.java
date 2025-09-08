package com.ecom.demo.Repository;

import com.ecom.demo.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface productRepo extends JpaRepository<Product,Integer> {

}
