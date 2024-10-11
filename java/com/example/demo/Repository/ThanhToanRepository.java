package com.example.demo.Repository;

import com.example.demo.Entity.KhuyenMai;
import com.example.demo.Entity.ThanhToan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThanhToanRepository extends JpaRepository<ThanhToan, Integer> {

}
