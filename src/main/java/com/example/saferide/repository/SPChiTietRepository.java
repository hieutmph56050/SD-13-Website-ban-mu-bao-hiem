package com.example.saferide.repository;


import com.example.saferide.entity.SPChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SPChiTietRepository extends JpaRepository<SPChiTiet, Integer> {

}
