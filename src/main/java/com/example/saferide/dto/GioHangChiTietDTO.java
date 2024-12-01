package com.example.saferide.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GioHangChiTietDTO {
    private int id;
    private String name;
    private Double price;
    private int quantity;
    private String imageUrl;
    private String size;
    private String color;

    // Getters and Setters
}
