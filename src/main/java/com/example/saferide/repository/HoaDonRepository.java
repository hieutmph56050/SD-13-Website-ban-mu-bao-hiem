package com.example.saferide.repository;


import com.example.saferide.entity.GioHang;
import com.example.saferide.entity.HoaDon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HoaDonRepository extends JpaRepository<HoaDon, Integer> {

    @Query("SELECT h FROM HoaDon h WHERE h.idTaiKhoan.ten LIKE %:HoTen%")
    List<HoaDon> findByCustomerName(String HoTen);


}
