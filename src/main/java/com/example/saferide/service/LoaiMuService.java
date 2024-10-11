package com.example.saferide.service;


import com.example.saferide.entity.LoaiMu;
import com.example.saferide.repository.LoaiMuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoaiMuService {
    @Autowired
    LoaiMuRepository loaimuRepository;

    public List<LoaiMu> getList() {
        return loaimuRepository.findAll();
    }

    public LoaiMu findById(Integer id) {
        return loaimuRepository.findById(id).get();
    }

    public void delete(Integer id) {
        loaimuRepository.deleteById(id);
    }

    public LoaiMu add(LoaiMu loaiMu) {
        return loaimuRepository.save(loaiMu);
    }

    public LoaiMu update(LoaiMu loaiMu) {
        return loaimuRepository.save(loaiMu);
    }
}
