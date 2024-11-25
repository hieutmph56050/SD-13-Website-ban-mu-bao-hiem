package com.example.saferide.repository;


import com.example.saferide.entity.LoaiMu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


@Repository
public interface LoaiMuRepository extends JpaRepository<LoaiMu, Integer>,JpaSpecificationExecutor<LoaiMu>  {

}
