package com.example.saferide.repository;


import com.example.saferide.entity.TaiKhoan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaiKhoanRepository extends JpaRepository<TaiKhoan, Integer> {

    @Query("SELECT tk FROM TaiKhoan tk WHERE tk.id = :id")
    Optional<TaiKhoan> findByIdTK(@Param("id") Integer id);


}