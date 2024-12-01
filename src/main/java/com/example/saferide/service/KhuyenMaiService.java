package com.example.saferide.service;

import com.example.saferide.entity.KhuyenMai;
import com.example.saferide.repository.KhuyenMaiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class KhuyenMaiService {
    @Autowired
    KhuyenMaiRepository khuyenmaiRepository;

    public List<KhuyenMai> getList() {
        return khuyenmaiRepository.findAll();
    }

    public KhuyenMai findById(Integer id) {
        return khuyenmaiRepository.findById(id).get();
    }

    public KhuyenMai add(KhuyenMai khuyenMai) {
        return khuyenmaiRepository.save(khuyenMai);
    }

    public KhuyenMai update(KhuyenMai khuyenMai, Integer id) {
        Optional<KhuyenMai> optional = khuyenmaiRepository.findById(id);
        return optional.map(khuyenMai1 -> {
            khuyenMai1.setMa(khuyenMai.getMa());
            khuyenMai1.setTen(khuyenMai.getTen());
            khuyenMai1.setGiaTri(khuyenMai.getGiaTri());
            khuyenMai1.setNgayBD(khuyenMai.getNgayBD());
            khuyenMai1.setNgayKT(khuyenMai.getNgayKT());
            khuyenMai1.setPTKM(khuyenMai.getPTKM());
            khuyenMai1.setDKKM(khuyenMai.getDKKM());
            khuyenMai1.setNguoiCapNhat(khuyenMai.getNguoiCapNhat());
            khuyenMai1.setNgayCapNhat(LocalDateTime.now());
            khuyenMai1.setTt(khuyenMai.getTt());
            return khuyenmaiRepository.save(khuyenMai1);
        }).orElse(null);
    }
    public KhuyenMai delete(Integer id) {
        Optional<KhuyenMai> optional = khuyenmaiRepository.findById(id);
        return optional.map(khuyenMai1 -> {
            khuyenmaiRepository.delete(khuyenMai1);
            return khuyenMai1;
        }).orElse(null);
    }
    // Tìm kiếm theo tên hoặc mã
    public List<KhuyenMai> search(String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            return khuyenmaiRepository.findAll();
        }
        return khuyenmaiRepository.findByMaContainingOrTenContaining(keyword, keyword);
    }

    // Phân trang
    public Page<KhuyenMai> getPage(Pageable pageable) {
        return khuyenmaiRepository.findAll(pageable);
    }
}
