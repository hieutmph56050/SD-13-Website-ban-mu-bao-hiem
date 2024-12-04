package com.example.saferide.repository;


import com.example.saferide.entity.ChatLieuVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatLieuVoRepository extends JpaRepository<ChatLieuVo, Integer> {
    // Tìm kiếm theo tất cả các trường
    @Query("SELECT c FROM ChatLieuVo c WHERE " +
            "LOWER(c.ma) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(c.ten) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(c.tt) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(c.moTa) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(c.nguoiTao) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(c.nguoiCapNhat) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<ChatLieuVo> searchByAllFields(String searchTerm);

    // Tìm kiếm có phân trang
    Page<ChatLieuVo> findAll(Pageable pageable);

}
