package com.example.saferide.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddToCartRequest {
    private int cartId;
    private int productId;
    private int idKichThuoc;
    private int quantity;
}
