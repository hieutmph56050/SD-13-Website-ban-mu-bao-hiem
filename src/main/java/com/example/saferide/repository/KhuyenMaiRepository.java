package com.example.saferide.repository;


import com.example.saferide.entity.KhuyenMai;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Repository
public interface KhuyenMaiRepository extends JpaRepository<KhuyenMai, Integer> {
    // Tìm kiếm theo mã hoặc tên khuyến mãi
    List<KhuyenMai> findByMaContainingOrTenContaining(String ma, String ten);
}
