package com.example.saferide.service;

import com.example.saferide.entity.SanPham;
import com.example.saferide.repository.SanPhamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class SanPhamService {
    @Autowired
    SanPhamRepository sanphamRepository;

    public List<SanPham> getList() {
        return sanphamRepository.findAll();
    }

    public SanPham findById(Integer id) {
        return sanphamRepository.findById(id).get();
    }

    public SanPham add(SanPham sanPham) {
        return sanphamRepository.save(sanPham);
    }

    public SanPham update(SanPham sanPham, Integer id) {
        Optional<SanPham> optional = sanphamRepository.findById(id);
        return optional.map(sanPham1 -> {
            sanPham1.setTen(sanPham.getTen());
            sanPham1.setMoTa(sanPham.getMoTa());
            sanPham1.setTt(sanPham.getTt());
            return sanphamRepository.save(sanPham1);
        }).orElse(null);
    }
    public SanPham delete(Integer id) {
        Optional<SanPham> optional = sanphamRepository.findById(id);
        return optional.map(sanPham1 -> {
            sanphamRepository.delete(sanPham1);
            return sanPham1;
        }).orElse(null);
    }
}
