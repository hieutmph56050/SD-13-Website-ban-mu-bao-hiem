package com.example.saferide.repository;


import com.example.saferide.entity.HoaDon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Repository
public interface HoaDonRepository extends JpaRepository<HoaDon, Integer> {

    @Query("SELECT h FROM HoaDon h WHERE h.idTaiKhoan.ten LIKE %:HoTen%")
    List<HoaDon> findByCustomerName(String HoTen);

    @Query("SELECT h FROM HoaDon h WHERE h.ma = :maHoaDon")
    Optional<HoaDon> findByMaHoaDon(String maHoaDon);

    @Query("SELECT h FROM HoaDon h WHERE " +
            "(:ma IS NULL OR h.ma LIKE %:ma%) AND " +
            "(:ghiChu IS NULL OR h.ghiChu LIKE %:ghiChu%) AND " +
            "(:diaChi IS NULL OR h.diaChi LIKE %:diaChi%) AND " +
            "(:nguoitao IS NULL OR h.nguoiTao LIKE %:nguoitao%)")
    Page<HoaDon> searchWithPaging(
            @Param("ma") String ma,
            @Param("ghiChu") String ghiChu,
            @Param("diaChi") String diaChi,
            @Param("nguoitao") String nguoitao,
            Pageable pageable
    );
}
