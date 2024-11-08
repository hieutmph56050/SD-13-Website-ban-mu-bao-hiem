package com.example.saferide.repository;


import com.example.saferide.entity.GioHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GioHangRepository extends JpaRepository<GioHang, Integer> {

    @Query("SELECT gh FROM GioHang gh WHERE gh.idTaiKhoan.id = :idTaiKhoan")
    GioHang findByIdTaiKhoanGH(@Param("idTaiKhoan") Integer idTaiKhoan);
    // Phương thức truy vấn giỏ hàng theo ID tài khoản

    @Query("SELECT gh FROM GioHang gh WHERE gh.idTaiKhoan.id = :idTaiKhoan")
    Optional<GioHang> findByIdTaiKhoan(@Param("idTaiKhoan") Integer idTaiKhoan);

}
