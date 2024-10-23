package com.example.saferide.service;


import com.example.saferide.entity.HoaDon;
import com.example.saferide.repository.HoaDonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public HoaDon update(HoaDon hoaDon) {
        return hoadonRepository.save(hoaDon);
    }
}
