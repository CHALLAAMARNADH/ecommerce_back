


package com.ecom.demo.dto;

import lombok.Data;

@Data   // Lombok will generate getters, setters, toString, etc.
public class CartItemDto {
    private int id;
    private int quantity;
}
