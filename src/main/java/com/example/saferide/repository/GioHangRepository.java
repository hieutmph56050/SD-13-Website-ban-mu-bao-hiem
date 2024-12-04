package com.example.saferide.repository;


import com.example.saferide.entity.GioHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

import java.util.Optional;

@Repository
public interface GioHangRepository extends JpaRepository<GioHang, Integer> {
    Page<GioHang> findByIdTaiKhoan_IdOrTt(Integer idTaiKhoan, Boolean tt, Pageable pageable);

    @Query("SELECT gh FROM GioHang gh WHERE gh.idTaiKhoan.id = :idTaiKhoan")
    GioHang findByIdTaiKhoanGH(@Param("idTaiKhoan") Integer idTaiKhoan);
    // Phương thức truy vấn giỏ hàng theo ID tài khoản

    @Query("SELECT gh FROM GioHang gh WHERE gh.idTaiKhoan.id = :idTaiKhoan")
    Optional<GioHang> findByIdTaiKhoan(@Param("idTaiKhoan") Integer idTaiKhoan);

}
