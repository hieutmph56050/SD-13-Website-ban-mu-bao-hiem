package com.example.demo.Service;

import com.example.demo.Entity.GioHangChiTiet;
import com.example.demo.Repository.GioHangChiTietRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GioHangChiTietService {
    @Autowired
    GioHangChiTietRepository giohangchitietRepository;

    public List<GioHangChiTiet> getList() {
        return giohangchitietRepository.findAll();
    }

    public GioHangChiTiet findById(Integer id) {
        return giohangchitietRepository.findById(id).get();
    }

    public void delete(Integer id) {
        giohangchitietRepository.deleteById(id);
    }

    public GioHangChiTiet add(GioHangChiTiet giohangChiTiet) {
        return giohangchitietRepository.save(giohangChiTiet);
    }

    public GioHangChiTiet update(GioHangChiTiet giohangChiTiet) {
        return giohangchitietRepository.save(giohangChiTiet);
    }
}
