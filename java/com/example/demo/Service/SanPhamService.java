package com.example.demo.Service;

import com.example.demo.Entity.SanPham;
import com.example.demo.Repository.SanPhamRepository;
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
