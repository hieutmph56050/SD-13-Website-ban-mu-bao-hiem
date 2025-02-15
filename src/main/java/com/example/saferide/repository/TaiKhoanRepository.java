package com.example.saferide.repository;


import com.example.saferide.entity.TaiKhoan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaiKhoanRepository extends JpaRepository<TaiKhoan, Integer> {

    @Query("SELECT tk FROM TaiKhoan tk WHERE tk.id = :id")
    Optional<TaiKhoan> findByIdTK(@Param("id") Integer id);

    Optional<TaiKhoan> findByTenDangNhap(String tenDangNhap);

    @Query("""
            SELECT tk FROM TaiKhoan tk where tk.sdt = :sdt
            """)
    TaiKhoan findByPhoneNumber(@Param("sdt") String sdt);

    @Query("""
                SELECT tk FROM TaiKhoan tk 
                WHERE 
                LOWER(tk.tenDangNhap) LIKE LOWER(CONCAT('%', :keyword, '%')) OR
                LOWER(tk.ten) LIKE LOWER(CONCAT('%', :keyword, '%')) OR
                LOWER(tk.email) LIKE LOWER(CONCAT('%', :keyword, '%')) OR
                LOWER(tk.sdt) LIKE LOWER(CONCAT('%', :keyword, '%')) OR
                LOWER(tk.cccd) LIKE LOWER(CONCAT('%', :keyword, '%'))
            """)
    Page<TaiKhoan> searchByKeyword(String keyword, Pageable pageable);

}
