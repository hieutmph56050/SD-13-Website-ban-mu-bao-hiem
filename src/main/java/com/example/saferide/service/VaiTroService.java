package com.example.saferide.service;

import com.example.saferide.entity.VaiTro;
import com.example.saferide.repository.VaiTroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class VaiTroService {
    @Autowired
    VaiTroRepository vaitroRepository;

    public List<VaiTro> getList() {
        return vaitroRepository.findAll();
    }

    public VaiTro findById(Integer id) {
        return vaitroRepository.findById(id).get();
    }

    public VaiTro add(VaiTro vaiTro) {
        return vaitroRepository.save(vaiTro);
    }

    public VaiTro update(VaiTro vaiTro, Integer id) {
        Optional<VaiTro> optional = vaitroRepository.findById(id);
        return optional.map(vaiTro1 -> {
            vaiTro1.setTen(vaiTro.getTen());
            vaiTro1.setMoTa(vaiTro.getMoTa());
            vaiTro1.setNguoiCapNhat(vaiTro.getNguoiCapNhat());
            vaiTro1.setNgayCapNhat(LocalDateTime.now());
            return vaitroRepository.save(vaiTro1);
        }).orElse(null);
    }
    public VaiTro delete(Integer id) {
        Optional<VaiTro> optional = vaitroRepository.findById(id);
        return optional.map(vaiTro1 -> {
            vaitroRepository.delete(vaiTro1);
            return vaiTro1;
        }).orElse(null);
    }

    public Page<VaiTro> search(String keyword, Pageable pageable) {
        return vaitroRepository.search(keyword, pageable);
    }


}
