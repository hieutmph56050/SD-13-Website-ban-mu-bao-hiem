package com.example.demo.Repository;

import com.example.demo.Entity.MauSac;
import com.example.demo.Entity.ThuongHieu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThuongHieuRepository extends JpaRepository<ThuongHieu, Integer> {

}
