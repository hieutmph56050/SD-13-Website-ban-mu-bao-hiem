package com.example.saferide.service;

import com.example.saferide.entity.GioHangChiTiet;
import com.example.saferide.repository.GioHangChiTietRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class GioHangChiTietService {
    @Autowired
    GioHangChiTietRepository sanphamRepository;

    public List<GioHangChiTiet> getList() {
        return sanphamRepository.findAll();
    }

    public GioHangChiTiet findById(Integer id) {
        return sanphamRepository.findById(id).get();
    }

    public GioHangChiTiet add(GioHangChiTiet sanPham) {
        return sanphamRepository.save(sanPham);
    }

    public GioHangChiTiet update(GioHangChiTiet sanPham, Integer id) {
        Optional<GioHangChiTiet> optional = sanphamRepository.findById(id);
        return optional.map(sanPham1 -> {
            sanPham1.setIdGioHang(sanPham.getIdGioHang());
            sanPham1.setIdSPCT(sanPham.getIdSPCT());
            sanPham1.setMa(sanPham.getMa());
            sanPham1.setDonGia(sanPham.getDonGia());
            sanPham1.setSl(sanPham.getSl());
            sanPham1.setNgayCapNhat(LocalDateTime.now());
            sanPham1.setTt(sanPham.getTt());
            return sanphamRepository.save(sanPham1);
        }).orElse(null);
    }
    public GioHangChiTiet delete(Integer id) {
        Optional<GioHangChiTiet> optional = sanphamRepository.findById(id);
        return optional.map(sanPham1 -> {
            sanphamRepository.delete(sanPham1);
            return sanPham1;
        }).orElse(null);
    }
    // Thêm phương thức phân trang và tìm kiếm
    public Page<GioHangChiTiet> searchByAllFields(String searchTerm, Pageable pageable) {
        return sanphamRepository.searchByAllFields(searchTerm, pageable);
    }

    public Page<GioHangChiTiet> getAllWithPagination(Pageable pageable) {
        return sanphamRepository.findAll(pageable);
    }
}
