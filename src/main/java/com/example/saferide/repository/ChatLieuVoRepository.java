package com.example.saferide.repository;


import com.example.saferide.entity.ChatLieuVo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatLieuVoRepository extends JpaRepository<ChatLieuVo, Integer> {

}
