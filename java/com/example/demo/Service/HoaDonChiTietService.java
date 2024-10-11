package com.example.demo.Service;

import com.example.demo.Entity.HoaDonChiTiet;
import com.example.demo.Repository.HoaDonChiTietRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public void delete(Integer id) {
        hoadonchitietRepository.deleteById(id);
    }

    public HoaDonChiTiet add(HoaDonChiTiet hoadonChiTiet) {
        return hoadonchitietRepository.save(hoadonChiTiet);
    }

    public HoaDonChiTiet update(HoaDonChiTiet hoadonChiTiet) {
        return hoadonchitietRepository.save(hoadonChiTiet);
    }
}
