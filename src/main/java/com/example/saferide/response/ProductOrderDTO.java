package com.example.saferide.response;

import com.example.saferide.entity.SPChiTiet;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductOrderDTO {
    private SPChiTiet idSPCT; // ID sản phẩm chi tiết
    private BigDecimal donGia; // Đơn giá sản phẩm
    private Integer soLuong;
}
