package com.example.saferide.repository;


import com.example.saferide.entity.LoaiKinh;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface LoaiKinhRepository extends JpaRepository<LoaiKinh, Integer> {
    @Query("SELECT lk FROM LoaiKinh lk " +
            "WHERE (:ma IS NULL OR lk.ma LIKE %:ma%) " +
            "AND (:ten IS NULL OR lk.ten LIKE %:ten%) " +
            "AND (:moTa IS NULL OR lk.moTa LIKE %:moTa%) " +
            "AND (:nguoiTao IS NULL OR lk.nguoiTao LIKE %:nguoiTao%) " +
            "AND (:nguoiCapNhat IS NULL OR lk.nguoiCapNhat LIKE %:nguoiCapNhat%) " +
            "AND (:tt IS NULL OR lk.tt LIKE %:tt%)")
    Page<LoaiKinh> search(
            @Param("ma") String ma,
            @Param("ten") String ten,
            @Param("moTa") String moTa,
            @Param("nguoiTao") String nguoiTao,
            @Param("nguoiCapNhat") String nguoiCapNhat,
            @Param("tt") String tt,
            Pageable pageable
    );
}
