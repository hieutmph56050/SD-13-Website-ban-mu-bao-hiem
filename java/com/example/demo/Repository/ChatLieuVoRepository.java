package com.example.demo.Repository;

import com.example.demo.Entity.ChatLieuVo;
import com.example.demo.Entity.LoaiMu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatLieuVoRepository extends JpaRepository<ChatLieuVo, Integer> {

}
