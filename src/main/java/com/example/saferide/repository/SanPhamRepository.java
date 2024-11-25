package com.example.saferide.repository;


import com.example.saferide.entity.HoaDon;
import com.example.saferide.entity.SanPham;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SanPhamRepository extends JpaRepository<SanPham, Integer> {
    @Query("SELECT s FROM SanPham s " +
            "WHERE LOWER(s.ten) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(s.moTa) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(s.tt) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<SanPham> search(@Param("keyword") String keyword, Pageable pageable);
}
