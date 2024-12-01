package com.example.saferide.repository;


import com.example.saferide.entity.MauSac;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface MauSacRepository extends JpaRepository<MauSac, Integer> {
    // Phương thức tìm kiếm theo tên, mã màu, mô tả
    @Query("SELECT m FROM MauSac m WHERE " +
            "(:ma IS NULL OR m.ma LIKE %:ma%) AND " +
            "(:ten IS NULL OR m.ten LIKE %:ten%) AND " +
            "(:moTa IS NULL OR m.moTa LIKE %:moTa%)")
    Page<MauSac> search(
            @Param("ma") String ma,
            @Param("ten") String ten,
            @Param("moTa") String moTa,
            Pageable pageable);
}

