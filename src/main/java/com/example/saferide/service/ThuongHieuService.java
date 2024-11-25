package com.example.saferide.service;

import com.example.saferide.entity.ThuongHieu;
import com.example.saferide.repository.ThuongHieuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

    public ThuongHieu add(ThuongHieu thuongHieu) {
        return thuonghieuRepository.save(thuongHieu);
    }

    public ThuongHieu update(ThuongHieu thuongHieu, Integer id) {
        Optional<ThuongHieu> optional = thuonghieuRepository.findById(id);
        return optional.map(thuongHieu1 -> {
            thuongHieu1.setTen(thuongHieu.getTen());
            thuongHieu1.setMoTa(thuongHieu.getMoTa());
            thuongHieu1.setNgayCapNhat(LocalDateTime.now());
            return thuonghieuRepository.save(thuongHieu1);
        }).orElse(null);
    }
    public ThuongHieu delete(Integer id) {
        Optional<ThuongHieu> optional = thuonghieuRepository.findById(id);
        return optional.map(thuongHieu1 -> {
            thuonghieuRepository.delete(thuongHieu1);
            return thuongHieu1;
        }).orElse(null);
    }
    public Page<ThuongHieu> search(String keyword, Pageable pageable) {
        return thuonghieuRepository.searchByKeyword(keyword, pageable);
    }
}
