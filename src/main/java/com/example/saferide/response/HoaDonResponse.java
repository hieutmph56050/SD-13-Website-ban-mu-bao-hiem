package com.example.saferide.response;

import com.example.saferide.entity.HoaDon;
import com.example.saferide.entity.HoaDonChiTiet;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class HoaDonResponse {
    List<DataHoaDonRes> data;

    @Getter
    @Setter
    public static class DataHoaDonRes {
        private HoaDon hoaDon;
        private List<HoaDonChiTiet> chiTietList;
    }

}