package com.example.saferide.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ThanhToanRequest {
    private String maHoaDon;
    private BigDecimal soTienKhachTra;
}