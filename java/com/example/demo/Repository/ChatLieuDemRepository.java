package com.example.demo.Repository;

import com.example.demo.Entity.ChatLieuDem;
import com.example.demo.Entity.ChatLieuVo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatLieuDemRepository extends JpaRepository<ChatLieuDem, Integer> {

}
