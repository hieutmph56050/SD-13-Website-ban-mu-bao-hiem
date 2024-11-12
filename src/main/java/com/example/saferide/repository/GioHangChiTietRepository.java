package com.example.saferide.repository;


import com.example.saferide.entity.GioHangChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;



@Repository
public interface GioHangChiTietRepository extends JpaRepository<GioHangChiTiet, Integer> {
    // Tìm kiếm theo tất cả các trường
    @Query("SELECT g FROM GioHangChiTiet g WHERE " +
            "LOWER(g.ma) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(g.tt) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    Page<GioHangChiTiet> searchByAllFields(String searchTerm, Pageable pageable);
}
