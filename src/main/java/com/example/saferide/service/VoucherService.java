package com.example.saferide.service;


import com.example.saferide.entity.Voucher;
import com.example.saferide.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoucherService {
    @Autowired
    VoucherRepository voucherRepository;

    public List<Voucher> getList() {
        return voucherRepository.findAll();
    }

    public Voucher findById(Integer id) {
        return voucherRepository.findById(id).get();
    }

    public void delete(Integer id) {
        voucherRepository.deleteById(id);
    }

    public Voucher add(Voucher voucher) {
        return voucherRepository.save(voucher);
    }

    public Voucher update(Voucher voucher) {
        return voucherRepository.save(voucher);
    }
}
