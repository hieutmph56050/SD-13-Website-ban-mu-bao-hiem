package com.example.saferide.service;

import com.example.saferide.entity.GioHang;
import com.example.saferide.repository.GioHangRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class GioHangService {
    @Autowired
    GioHangRepository giohangRepository;

    public List<GioHang> getList() {
        return giohangRepository.findAll();
    }

    public GioHang findById(Integer id) {
        return giohangRepository.findById(id).get();
    }

    public GioHang add(GioHang gioHang) {
        return giohangRepository.save(gioHang);
    }

    public GioHang update(GioHang gioHang, Integer id) {
        Optional<GioHang> optional = giohangRepository.findById(id);
        return optional.map(gioHang1 -> {
            gioHang1.setIdTaiKhoan(gioHang.getIdTaiKhoan());
            gioHang1.setNgayCapNhat(LocalDateTime.now());
            gioHang1.setTt(gioHang.isTt());
            return giohangRepository.save(gioHang1);
        }).orElse(null);
    }
    public void deleteById(Integer id) {
        giohangRepository.deleteById(id);
    }
    public Page<GioHang> search(Integer idTaiKhoan, Boolean tt, Pageable pageable) {
        return giohangRepository.findByIdTaiKhoan_IdOrTt(idTaiKhoan, tt, pageable);
    }

}
