package com.example.saferide.service;

import com.example.saferide.entity.LoaiMu;
import com.example.saferide.repository.LoaiMuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class LoaiMuService {
    @Autowired
    LoaiMuRepository loaimuRepository;

    public List<LoaiMu> getList() {
        return loaimuRepository.findAll();
    }

    public LoaiMu findById(Integer id) {
        return loaimuRepository.findById(id).get();
    }

    public LoaiMu add(LoaiMu loaiMu) {
        return loaimuRepository.save(loaiMu);
    }

    public LoaiMu update(LoaiMu loaiMu, Integer id) {
        Optional<LoaiMu> optional = loaimuRepository.findById(id);
        return optional.map(loaiMu1 -> {
            loaiMu1.setMa(loaiMu.getMa());
            loaiMu1.setTen(loaiMu.getTen());
            loaiMu1.setMoTa(loaiMu.getMoTa());
            loaiMu1.setNguoiCapNhat(loaiMu.getNguoiCapNhat());
            loaiMu1.setNgayCapNhat(LocalDateTime.now());
            loaiMu1.setTt(loaiMu.getTt());
            return loaimuRepository.save(loaiMu1);
        }).orElse(null);
    }
    public LoaiMu delete(Integer id) {
        Optional<LoaiMu> optional = loaimuRepository.findById(id);
        return optional.map(loaiMu1 -> {
            loaimuRepository.delete(loaiMu1);
            return loaiMu1;
        }).orElse(null);
    }
    // Phương thức để tạo Specification cho việc tìm kiếm
    private Specification<LoaiMu> buildSearchSpecification(String searchTerm) {
        return (root, query, criteriaBuilder) -> {
            if (searchTerm == null || searchTerm.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.or(
                    criteriaBuilder.like(root.get("ma"), "%" + searchTerm + "%"),
                    criteriaBuilder.like(root.get("ten"), "%" + searchTerm + "%"),
                    criteriaBuilder.like(root.get("moTa"), "%" + searchTerm + "%"),
                    criteriaBuilder.like(root.get("nguoiTao"), "%" + searchTerm + "%"),
                    criteriaBuilder.like(root.get("nguoiCapNhat"), "%" + searchTerm + "%"),
                    criteriaBuilder.like(root.get("tt"), "%" + searchTerm + "%")
            );
        };
    }

    public Page<LoaiMu> getListWithPaginationAndSearch(String searchTerm, Pageable pageable) {
        Specification<LoaiMu> spec = buildSearchSpecification(searchTerm);
        return loaimuRepository.findAll(spec, pageable);
    }
}
