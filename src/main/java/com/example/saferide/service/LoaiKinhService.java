package com.example.saferide.service;


import com.example.saferide.entity.LoaiKinh;
import com.example.saferide.repository.LoaiKinhRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoaiKinhService {
    @Autowired
    LoaiKinhRepository loaikinhRepository;

    public List<LoaiKinh> getList() {
        return loaikinhRepository.findAll();
    }

    public LoaiKinh findById(Integer id) {
        return loaikinhRepository.findById(id).get();
    }

    public void delete(Integer id) {
        loaikinhRepository.deleteById(id);
    }

    public LoaiKinh add(LoaiKinh loaiKinh) {
        return loaikinhRepository.save(loaiKinh);
    }

    public LoaiKinh update(LoaiKinh loaiKinh) {
        return loaikinhRepository.save(loaiKinh);
    }
}
