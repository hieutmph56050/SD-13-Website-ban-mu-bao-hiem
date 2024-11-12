package com.example.saferide.repository;


import com.example.saferide.entity.ChatLieuDem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


@Repository
public interface ChatLieuDemRepository extends JpaRepository<ChatLieuDem, Integer> {
    // Tìm kiếm theo tất cả các trường
    @Query("SELECT c FROM ChatLieuDem c WHERE " +
            "LOWER(c.ma) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(c.ten) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(c.tt) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(c.nguoiTao) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(c.nguoiCapNhat) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(c.moTa) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<ChatLieuDem> searchByAllFields(String searchTerm);
}
