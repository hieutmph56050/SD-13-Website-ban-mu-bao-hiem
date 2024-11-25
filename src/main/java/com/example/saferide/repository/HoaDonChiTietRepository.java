package com.example.saferide.repository;


import com.example.saferide.entity.HoaDonChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface HoaDonChiTietRepository extends JpaRepository<HoaDonChiTiet, Integer> {

    @Query("SELECT hdc FROM HoaDonChiTiet hdc WHERE hdc.idHoaDon.id = :hoaDonId")
    List<HoaDonChiTiet> findByHoaDonId(Integer hoaDonId);


    @Query("SELECT COUNT(hdct) FROM HoaDonChiTiet hdct WHERE hdct.idHoaDon.id = :hoaDonId")
    long countByHoaDonId(Integer hoaDonId);

    @Query("SELECT hdc FROM HoaDonChiTiet hdc WHERE hdc.idHoaDon.id = :hoaDonId AND hdc.idSPCT.id = :sanPhamId")
    Optional<HoaDonChiTiet> findByHoaDonIdAndSanPhamId(@Param("hoaDonId") Integer hoaDonId, @Param("sanPhamId") Integer sanPhamId);

    @Query("SELECT h FROM HoaDonChiTiet h " +
            "WHERE (:mahdct IS NULL OR h.mahdct LIKE %:mahdct%) " +
            "AND (:tongTien IS NULL OR h.tongTien = :tongTien) " +
            "AND (:sl IS NULL OR h.sl = :sl) " +
            "AND (:ghiChu IS NULL OR h.ghiChu LIKE %:ghiChu%) " +
            "AND (:tt IS NULL OR h.tt LIKE %:tt%)")
    Page<HoaDonChiTiet> search(@Param("mahdct") String mahdct,
                               @Param("tongTien") BigDecimal tongTien,
                               @Param("sl") Integer sl,
                               @Param("ghiChu") String ghiChu,
                               @Param("tt") String tt,
                               Pageable pageable);
}
