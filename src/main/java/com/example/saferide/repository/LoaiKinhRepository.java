package com.example.saferide.repository;


import com.example.saferide.entity.LoaiKinh;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoaiKinhRepository extends JpaRepository<LoaiKinh, Integer> {

}
