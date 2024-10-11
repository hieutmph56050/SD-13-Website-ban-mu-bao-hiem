package com.example.demo.Service;

import com.example.demo.Entity.SPChiTiet;
import com.example.demo.Repository.SPChiTietRepository;
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
