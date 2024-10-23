package com.example.saferide.service;


import com.example.saferide.entity.SPChiTiet;
import com.example.saferide.repository.SPChiTietRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SPChiTietService {
    @Autowired
    SPChiTietRepository spchitietRepository;

    public List<SPChiTiet> getList() {
        return spchitietRepository.findAll();
    }

    public SPChiTiet findById(Integer id) {
        return spchitietRepository.findById(id).get();
    }

    public void delete(Integer id) {
        spchitietRepository.deleteById(id);
    }

    public SPChiTiet add(SPChiTiet spChiTiet) {
        return spchitietRepository.save(spChiTiet);
    }

    public SPChiTiet update(SPChiTiet spChiTiet) {
        return spchitietRepository.save(spChiTiet);
    }
}
