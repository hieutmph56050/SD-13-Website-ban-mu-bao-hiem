package com.example.saferide.response;

import com.example.saferide.entity.HoaDon;
import com.example.saferide.entity.HoaDonChiTiet;
import com.example.saferide.entity.SPChiTiet;

import java.util.List;

public class InvoiceResponse {
    private HoaDon hoaDon;
    private List<HoaDonChiTiet> listHoaDonChiTiet;
    private Integer selectedHoaDonId;
    private List<HoaDon> listHoaDon;
    private List<SPChiTiet> listSanPhamChiTiet;

    // Getters and setters for all fields
    public HoaDon getHoaDon() {
        return hoaDon;
    }

    public void setHoaDon(HoaDon hoaDon) {
        this.hoaDon = hoaDon;
    }

    public List<HoaDonChiTiet> getListHoaDonChiTiet() {
        return listHoaDonChiTiet;
    }

    public void setListHoaDonChiTiet(List<HoaDonChiTiet> listHoaDonChiTiet) {
        this.listHoaDonChiTiet = listHoaDonChiTiet;
    }

    public Integer getSelectedHoaDonId() {
        return selectedHoaDonId;
    }

    public void setSelectedHoaDonId(Integer selectedHoaDonId) {
        this.selectedHoaDonId = selectedHoaDonId;
    }

    public List<HoaDon> getListHoaDon() {
        return listHoaDon;
    }

    public void setListHoaDon(List<HoaDon> listHoaDon) {
        this.listHoaDon = listHoaDon;
    }

    public List<SPChiTiet> getListSanPhamChiTiet() {
        return listSanPhamChiTiet;
    }

    public void setListSanPhamChiTiet(List<SPChiTiet> listSanPhamChiTiet) {
        this.listSanPhamChiTiet = listSanPhamChiTiet;
    }

}