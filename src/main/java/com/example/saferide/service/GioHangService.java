package com.example.saferide.service;


import com.example.saferide.entity.GioHang;
import com.example.saferide.repository.GioHangRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public GioHang update(GioHang gioHang) {
        return giohangRepository.save(gioHang);
    }
}
