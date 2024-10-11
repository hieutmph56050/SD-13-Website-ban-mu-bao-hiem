package com.example.demo.Repository;

import com.example.demo.Entity.TaiKhoan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaiKhoanRepository extends JpaRepository<TaiKhoan, Integer> {
}
