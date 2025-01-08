package com.example.saferide.response;

import com.example.saferide.entity.HoaDon;
import com.example.saferide.entity.HoaDonChiTiet;
import com.example.saferide.entity.SPChiTiet;
import com.example.saferide.entity.SanPham;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SanPhamResponse {
    SanPhamRes data;

    @Getter
    @Setter
    public static class SanPhamRes {
        private SanPham sanPham;
        private List<SPChiTiet> chiTietList;
    }

}