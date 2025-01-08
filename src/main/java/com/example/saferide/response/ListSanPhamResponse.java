package com.example.saferide.response;

import com.example.saferide.entity.SPChiTiet;
import com.example.saferide.entity.SanPham;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ListSanPhamResponse {
    List<SanPhamRes> data;

    @Getter
    @Setter
    public static class SanPhamRes {
        private SanPham sanPham;
        private List<SPChiTiet> chiTietList;
    }

}