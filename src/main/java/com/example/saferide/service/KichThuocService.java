package com.example.saferide.service;

import com.example.saferide.entity.KichThuoc;
import com.example.saferide.repository.KichThuocRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class KichThuocService {
    @Autowired
    KichThuocRepository kichthuocRepository;

    public List<KichThuoc> getList() {
        return kichthuocRepository.findAll();
    }

    public KichThuoc findById(Integer id) {
        return kichthuocRepository.findById(id).get();
    }

    public KichThuoc add(KichThuoc kichThuoc) {
        return kichthuocRepository.save(kichThuoc);
    }

    public KichThuoc update(KichThuoc kichThuoc, Integer id) {
        Optional<KichThuoc> optional = kichthuocRepository.findById(id);
        return optional.map(kichThuoc1 -> {
            kichThuoc1.setTen(kichThuoc.getTen());
            kichThuoc1.setMoTa(kichThuoc.getMoTa());
            kichThuoc1.setTt(kichThuoc.getTt());
            return kichthuocRepository.save(kichThuoc1);
        }).orElse(null);
    }
    public KichThuoc delete(Integer id) {
        Optional<KichThuoc> optional = kichthuocRepository.findById(id);
        return optional.map(kichThuoc1 -> {
            kichthuocRepository.delete(kichThuoc1);
            return kichThuoc1;
        }).orElse(null);
    }
}