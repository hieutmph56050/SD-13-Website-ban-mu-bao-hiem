package com.example.saferide.repository;


import com.example.saferide.entity.VaiTro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface VaiTroRepository extends JpaRepository<VaiTro, Integer> {
    @Query("SELECT v FROM VaiTro v WHERE " +
            "LOWER(v.ten) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(v.moTa) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(v.nguoiTao) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(v.nguoiCapNhat) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<VaiTro> search( String keyword, Pageable pageable);


}
