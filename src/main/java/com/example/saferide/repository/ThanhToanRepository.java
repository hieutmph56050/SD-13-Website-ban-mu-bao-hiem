package com.example.saferide.repository;


import com.example.saferide.entity.ThanhToan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface ThanhToanRepository extends JpaRepository<ThanhToan, Integer> {
    @Query("""
    SELECT tt FROM ThanhToan tt 
    WHERE (LOWER(tt.ten) LIKE LOWER(CONCAT('%', :keyword, '%')) 
           OR LOWER(tt.nguoiTao) LIKE LOWER(CONCAT('%', :keyword, '%'))
           OR LOWER(tt.nguoiCapNhat) LIKE LOWER(CONCAT('%', :keyword, '%'))
           OR LOWER(tt.tt) LIKE LOWER(CONCAT('%', :keyword, '%')))
    """)
    Page<ThanhToan> search( String keyword, Pageable pageable);
}
