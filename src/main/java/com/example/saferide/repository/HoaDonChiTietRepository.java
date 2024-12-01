package com.example.saferide.repository;


import com.example.saferide.entity.HoaDonChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

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

        @Query("SELECT hdc FROM HoaDonChiTiet hdc WHERE hdc.idHoaDon.loaiHoaDon = :loaiHoaDon")
        List<HoaDonChiTiet> findHDByLoaiHoaDon(@Param("loaiHoaDon") Integer loaiHoaDon);


}
