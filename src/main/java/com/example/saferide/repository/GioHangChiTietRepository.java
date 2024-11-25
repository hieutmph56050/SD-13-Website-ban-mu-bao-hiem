package com.example.saferide.repository;


import com.example.saferide.entity.GioHang;
import com.example.saferide.entity.GioHangChiTiet;
import com.example.saferide.entity.SPChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GioHangChiTietRepository extends JpaRepository<GioHangChiTiet, Integer> {

    Optional<GioHangChiTiet> findByIdGioHangAndIdSPCT(GioHang gioHang, SPChiTiet spChiTiet);

    List<GioHangChiTiet> findByIdGioHang(GioHang idGioHang);

}