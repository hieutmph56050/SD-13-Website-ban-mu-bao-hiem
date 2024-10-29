package com.example.saferide.service;

import com.example.saferide.entity.HoaDonChiTiet;
import com.example.saferide.repository.HoaDonChiTietRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class HoaDonChiTietService {
    @Autowired
    HoaDonChiTietRepository hoadonchitietRepository;

    public List<HoaDonChiTiet> getList() {
        return hoadonchitietRepository.findAll();
    }

    public HoaDonChiTiet findById(Integer id) {
        return hoadonchitietRepository.findById(id).get();
    }

    public HoaDonChiTiet add(HoaDonChiTiet hoadonChiTiet) {
        return hoadonchitietRepository.save(hoadonChiTiet);
    }

    public HoaDonChiTiet update(HoaDonChiTiet hoadonChiTiet, Integer id) {
        Optional<HoaDonChiTiet> optional = hoadonchitietRepository.findById(id);
        return optional.map(hoadonChiTiet1 -> {
            hoadonChiTiet1.setIdHoaDon(hoadonChiTiet.getIdHoaDon());
            hoadonChiTiet1.setIdSPCT(hoadonChiTiet.getIdSPCT());
            hoadonChiTiet1.setMahdct(hoadonChiTiet.getMahdct());
            hoadonChiTiet1.setTongTien(hoadonChiTiet.getTongTien());
            hoadonChiTiet1.setSl(hoadonChiTiet.getSl());
            hoadonChiTiet1.setGhiChu(hoadonChiTiet.getGhiChu());
            hoadonChiTiet1.setNgayCapNhat(LocalDateTime.now());
            hoadonChiTiet1.setTt(hoadonChiTiet.getTt());
            return hoadonchitietRepository.save(hoadonChiTiet1);
        }).orElse(null);
    }
    public HoaDonChiTiet delete(Integer id) {
        Optional<HoaDonChiTiet> optional = hoadonchitietRepository.findById(id);
        return optional.map(hoadonChiTiet1 -> {
            hoadonchitietRepository.delete(hoadonChiTiet1);
            return hoadonChiTiet1;
        }).orElse(null);
    }
}
