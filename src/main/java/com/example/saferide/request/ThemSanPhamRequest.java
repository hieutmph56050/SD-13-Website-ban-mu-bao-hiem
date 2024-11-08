package com.example.saferide.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ThemSanPhamRequest {
    // Getters and setters
    private String maHoaDon;
    private Integer sanPhamId;
    private BigDecimal soLuong;
}
