package com.example.saferide.service;


import com.example.saferide.entity.MauSac;
import com.example.saferide.repository.MauSacRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MauSacService {
    @Autowired
    MauSacRepository mausacRepository;

    public List<MauSac> getList() {
        return mausacRepository.findAll();
    }

    public MauSac findById(Integer id) {
        return mausacRepository.findById(id).get();
    }

    public void delete(Integer id) {
        mausacRepository.deleteById(id);
    }

    public MauSac add(MauSac mauSac) {
        return mausacRepository.save(mauSac);
    }

    public MauSac update(MauSac mauSac) {
        return mausacRepository.save(mauSac);
    }
}
