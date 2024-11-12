package com.example.saferide.repository;


import com.example.saferide.entity.ThuongHieu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import java.util.List;

@Repository
public interface ThuongHieuRepository extends JpaRepository<ThuongHieu, Integer> {

    @Query("SELECT t FROM ThuongHieu t WHERE " +
            "LOWER(t.ten) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(t.moTa) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<ThuongHieu> searchByKeyword(String keyword, Pageable pageable);
}
