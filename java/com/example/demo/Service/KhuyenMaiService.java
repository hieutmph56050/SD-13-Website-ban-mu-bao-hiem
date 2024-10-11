package com.example.demo.Service;

import com.example.demo.Entity.KhuyenMai;
import com.example.demo.Repository.KhuyenMaiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KhuyenMaiService {
    @Autowired
    KhuyenMaiRepository khuyenmaiRepository;

    public List<KhuyenMai> getList() {
        return khuyenmaiRepository.findAll();
    }

    public KhuyenMai findById(Integer id) {
        return khuyenmaiRepository.findById(id).get();
    }

    public void delete(Integer id) {
        khuyenmaiRepository.deleteById(id);
    }

    public KhuyenMai add(KhuyenMai khuyenMai) {
        return khuyenmaiRepository.save(khuyenMai);
    }

    public KhuyenMai update(KhuyenMai khuyenMai) {
        return khuyenmaiRepository.save(khuyenMai);
    }
}
