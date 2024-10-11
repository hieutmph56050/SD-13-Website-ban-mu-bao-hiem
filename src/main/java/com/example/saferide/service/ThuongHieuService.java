package com.example.saferide.service;


import com.example.saferide.entity.ThuongHieu;
import com.example.saferide.repository.ThuongHieuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThuongHieuService {
    @Autowired
    ThuongHieuRepository thuonghieuRepository;

    public List<ThuongHieu> getList() {
        return thuonghieuRepository.findAll();
    }

    public ThuongHieu findById(Integer id) {
        return thuonghieuRepository.findById(id).get();
    }

    public void delete(Integer id) {
        thuonghieuRepository.deleteById(id);
    }

    public ThuongHieu add(ThuongHieu thuongHieu) {
        return thuonghieuRepository.save(thuongHieu);
    }

    public ThuongHieu update(ThuongHieu thuongHieu) {
        return thuonghieuRepository.save(thuongHieu);
    }
}
