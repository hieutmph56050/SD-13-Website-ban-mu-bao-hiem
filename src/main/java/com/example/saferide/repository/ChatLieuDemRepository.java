package com.example.saferide.repository;


import com.example.saferide.entity.ChatLieuDem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatLieuDemRepository extends JpaRepository<ChatLieuDem, Integer> {

}
