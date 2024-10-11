package com.example.saferide.service;


import com.example.saferide.entity.SanPham;
import com.example.saferide.repository.SanPhamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

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

    public void delete(Integer id) {
        sanphamRepository.deleteById(id);
    }

    public SanPham add(SanPham sanPham) {
        return sanphamRepository.save(sanPham);
    }

    public SanPham update(SanPham sanPham) {
        return sanphamRepository.save(sanPham);
    }
}
