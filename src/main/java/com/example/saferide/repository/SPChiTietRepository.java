package com.example.saferide.repository;


import com.example.saferide.entity.HoaDonChiTiet;
import com.example.saferide.entity.SPChiTiet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SPChiTietRepository extends JpaRepository<SPChiTiet, Integer> {

    Page<SPChiTiet> findAll(Pageable pageable);

    @Query("SELECT spc FROM SPChiTiet spc WHERE spc.idSanPham.id = :IDSP")
    List<SPChiTiet> findByIDSP(Integer IDSP);

    List<SPChiTiet> findByMaContainingOrMoTaCTContainingOrXuatXuContainingAndSlGreaterThanEqual(
            String ma, String moTaCT, String xuatXu, int sl);

    @Query("""
        SELECT spct FROM SPChiTiet spct WHERE spct.id = :id and spct.idKichThuoc = :idKichThuoc
""")
    Optional<SPChiTiet> findByIdAndIdKichThuoc_Id(int id, int idKichThuoc);
}
