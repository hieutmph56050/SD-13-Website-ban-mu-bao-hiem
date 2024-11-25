package com.example.saferide.service;

import com.example.saferide.entity.LoaiKinh;
import com.example.saferide.repository.LoaiKinhRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

    public LoaiKinh add(LoaiKinh loaiKinh) {
        return loaikinhRepository.save(loaiKinh);
    }

    public LoaiKinh update(LoaiKinh loaiKinh, Integer id) {
        Optional<LoaiKinh> optional = loaikinhRepository.findById(id);
        return optional.map(loaiKinh1 -> {
            loaiKinh1.setMa(loaiKinh.getMa());
            loaiKinh1.setTen(loaiKinh.getTen());
            loaiKinh1.setMoTa(loaiKinh.getMoTa());

            loaiKinh1.setNguoiCapNhat(loaiKinh.getNguoiCapNhat());
            loaiKinh1.setNgayCapNhat(LocalDateTime.now());
            loaiKinh1.setTt(loaiKinh.getTt());
            return loaikinhRepository.save(loaiKinh1);
        }).orElse(null);
    }
    public LoaiKinh delete(Integer id) {
        Optional<LoaiKinh> optional = loaikinhRepository.findById(id);
        return optional.map(loaiKinh1 -> {
            loaikinhRepository.delete(loaiKinh1);
            return loaiKinh1;
        }).orElse(null);
    }
    public Page<LoaiKinh> search(String ma, String ten, String moTa, String nguoiTao, String nguoiCapNhat, String tt, Pageable pageable) {
        return loaikinhRepository.search(ma, ten, moTa, nguoiTao, nguoiCapNhat, tt, pageable);
    }
}
