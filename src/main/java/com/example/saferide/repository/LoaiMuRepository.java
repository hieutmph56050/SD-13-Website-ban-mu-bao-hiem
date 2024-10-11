package com.example.saferide.repository;


import com.example.saferide.entity.LoaiMu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoaiMuRepository extends JpaRepository<LoaiMu, Integer> {

}
