package com.example.demo.Repository;

import com.example.demo.Entity.LoaiMu;
import com.example.demo.Entity.VaiTro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VaiTroRepository extends JpaRepository<VaiTro, Integer> {

}
