package com.example.demo.Repository;

import com.example.demo.Entity.HoaDon;
import com.example.demo.Entity.SPChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SPChiTietRepository extends JpaRepository<SPChiTiet, Integer> {

}
