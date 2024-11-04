package com.example.saferide.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ThanhToanRequest {
    // Getters và Setters
    private String maHoaDon;
    private BigDecimal soTienKhachTra;
}