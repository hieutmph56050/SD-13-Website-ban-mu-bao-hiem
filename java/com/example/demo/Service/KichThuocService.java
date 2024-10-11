package com.example.demo.Service;

import com.example.demo.Entity.KichThuoc;
import com.example.demo.Repository.KichThuocRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public void delete(Integer id) {
        kichthuocRepository.deleteById(id);
    }

    public KichThuoc add(KichThuoc kichThuoc) {
        return kichthuocRepository.save(kichThuoc);
    }

    public KichThuoc update(KichThuoc kichThuoc) {
        return kichthuocRepository.save(kichThuoc);
    }
}
