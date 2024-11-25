package com.example.saferide.service;

import com.example.saferide.entity.ThanhToan;
import com.example.saferide.repository.ThanhToanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ThanhToanService {
    @Autowired
    ThanhToanRepository thanhtoanRepository;

    public List<ThanhToan> getList() {
        return thanhtoanRepository.findAll();
    }

    public ThanhToan findById(Integer id) {
        return thanhtoanRepository.findById(id).get();
    }

    public ThanhToan add(ThanhToan thanhtoan) {
        return thanhtoanRepository.save(thanhtoan);
    }

    public ThanhToan update(ThanhToan thanhtoan, Integer id) {
        Optional<ThanhToan> optional = thanhtoanRepository.findById(id);
        return optional.map(thanhtoan1 -> {
            thanhtoan1.setIdHoaDon(thanhtoan.getIdHoaDon());
            thanhtoan1.setTen(thanhtoan.getTen());
            thanhtoan1.setTongTien(thanhtoan.getTongTien());
            thanhtoan1.setNgayThanhToan(thanhtoan.getNgayThanhToan());
            thanhtoan1.setNguoiCapNhat("Admin");
            thanhtoan1.setNgayCapNhat(LocalDateTime.now());
            thanhtoan1.setTt(thanhtoan.getTt());
            return thanhtoanRepository.save(thanhtoan1);
        }).orElse(null);
    }
    public ThanhToan delete(Integer id) {
        Optional<ThanhToan> optional = thanhtoanRepository.findById(id);
        return optional.map(thanhtoan1 -> {
            thanhtoanRepository.delete(thanhtoan1);
            return thanhtoan1;
        }).orElse(null);
    }

    public Page<ThanhToan> search(String keyword, Pageable pageable) {
        return thanhtoanRepository.search(keyword, pageable);
    }
}
