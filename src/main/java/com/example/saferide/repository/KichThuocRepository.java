package com.example.saferide.repository;


import com.example.saferide.entity.KichThuoc;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KichThuocRepository extends JpaRepository<KichThuoc, Integer> {

    // Tìm kiếm theo ma, ten, moTa, nguoiTao, tt
    Page<KichThuoc> findByMaContainingOrTenContainingOrMoTaContainingOrNguoiTaoContainingOrTtContaining(
            String ma, String ten, String moTa, String nguoiTao, String tt, Pageable pageable);
}
