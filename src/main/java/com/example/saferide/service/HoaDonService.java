package com.example.saferide.service;

import com.example.saferide.entity.HoaDon;
import com.example.saferide.repository.HoaDonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class HoaDonService{
    @Autowired
    HoaDonRepository hoadonRepository;

    public List<HoaDon> getList() {
        return hoadonRepository.findAll();
    }

    public HoaDon findById(Integer id) {
        return hoadonRepository.findById(id).get();
    }

    public HoaDon add(HoaDon hoaDon) {
        return hoadonRepository.save(hoaDon);
    }

    public HoaDon update(HoaDon hoaDon, Integer id) {
        Optional<HoaDon> optional = hoadonRepository.findById(id);
        return optional.map(hoaDon1 -> {
            hoaDon1.setMa(hoaDon.getMa());
            hoaDon1.setIdTaiKhoan(hoaDon.getIdTaiKhoan());
            hoaDon1.setIdVoucher(hoaDon.getIdVoucher());
            hoaDon1.setLoaiHoaDon(hoaDon.getLoaiHoaDon());
            hoaDon1.setNgayGiaoHang(hoaDon.getNgayGiaoHang());
            hoaDon1.setNgayNhan(hoaDon.getNgayNhan());
            hoaDon1.setGiaGiam(hoaDon.getGiaGiam());
            hoaDon1.setTongTien(hoaDon.getTongTien());
            hoaDon1.setSoTienDaTra(hoaDon.getSoTienDaTra());
            hoaDon1.setGhiChu(hoaDon.getGhiChu());
            hoaDon1.setDiaChi(hoaDon.getDiaChi());
            hoaDon1.setNguoiCapNhat("Admin");
            hoaDon1.setNgayCapNhat(LocalDateTime.now());
            hoaDon1.setTt(hoaDon.getTt());
            return hoadonRepository.save(hoaDon1);
        }).orElse(null);
    }
//    public HoaDon delete(Integer id) {
//        Optional<HoaDon> optional = hoadonRepository.findById(id);
//        return optional.map(hoaDon1 -> {
//            hoadonRepository.delete(hoaDon1);
//            return hoaDon1;
//        }).orElse(null);
//    }

//    public Page<HoaDon> searchWithPaging(String ma, String ghiChu, String diaChi, String nguoiTao, Pageable pageable) {
//        return hoadonRepository.searchWithPaging(ma, ghiChu, diaChi, nguoiTao, pageable);
//    }
}
