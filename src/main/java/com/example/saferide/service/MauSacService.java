package com.example.saferide.service;

import com.example.saferide.entity.MauSac;
import com.example.saferide.repository.MauSacRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

    public MauSac add(MauSac mauSac) {
        return mausacRepository.save(mauSac);
    }

    public MauSac update(MauSac mauSac, Integer id) {
        Optional<MauSac> optional = mausacRepository.findById(id);
        return optional.map(mauSac1 -> {
            mauSac1.setMa(mauSac.getMa());
            mauSac1.setTen(mauSac.getTen());
            mauSac1.setMoTa(mauSac.getMoTa());
            mauSac1.setNgayCapNhat(LocalDateTime.now());
            return mausacRepository.save(mauSac1);
        }).orElse(null);
    }
    public MauSac delete(Integer id) {
        Optional<MauSac> optional = mausacRepository.findById(id);
        return optional.map(mauSac1 -> {
            mausacRepository.delete(mauSac1);
            return mauSac1;
        }).orElse(null);
    }
}
