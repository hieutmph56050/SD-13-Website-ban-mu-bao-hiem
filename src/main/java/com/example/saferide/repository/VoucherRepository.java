package com.example.saferide.repository;


import com.example.saferide.entity.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


@Repository
public interface VoucherRepository extends JpaRepository<Voucher, Integer> {
    @Query("SELECT v FROM Voucher v WHERE " +
            "(:keyword IS NULL OR v.ma LIKE %:keyword% OR v.ten LIKE %:keyword% OR v.moTa LIKE %:keyword%)")
    List<Voucher> searchVoucher(String keyword);
    Page<Voucher> findAll(Pageable pageable);

}
