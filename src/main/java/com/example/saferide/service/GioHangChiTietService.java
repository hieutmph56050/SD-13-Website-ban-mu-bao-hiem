package com.example.saferide.service;

import com.example.saferide.entity.GioHang;
import com.example.saferide.entity.GioHangChiTiet;
import com.example.saferide.repository.GioHangChiTietRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GioHangChiTietService {
    @Autowired
    private GioHangChiTietRepository gioHangChiTietRepository;

    public List<GioHangChiTiet> getGioHangChiTietByGioHang(GioHang gioHang) {
        return gioHangChiTietRepository.findByIdGioHang(gioHang);
    }

}