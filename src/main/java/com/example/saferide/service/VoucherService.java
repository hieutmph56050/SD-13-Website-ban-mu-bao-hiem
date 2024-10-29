package com.example.saferide.service;

import com.example.saferide.entity.Voucher;
import com.example.saferide.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

    public Voucher add(Voucher voucher) {
        return voucherRepository.save(voucher);
    }

    public Voucher update(Voucher voucher, Integer id) {
        Optional<Voucher> optional = voucherRepository.findById(id);
        return optional.map(voucher1 -> {
            voucher1.setMa(voucher.getMa());
            voucher1.setTen(voucher.getTen());
            voucher1.setGiaTri(voucher.getGiaTri());
            voucher1.setGiaTriMax(voucher.getGiaTriMax());
            voucher1.setGioihan(voucher.getGioihan());
            voucher1.setMoTa(voucher.getMoTa());
            voucher1.setTt(voucher.getTt());

            voucher1.setNgayKT(LocalDateTime.now());
            return voucherRepository.save(voucher1);
        }).orElse(null);
    }
    public Voucher delete(Integer id) {
        Optional<Voucher> optional = voucherRepository.findById(id);
        return optional.map(voucher1 -> {
            voucherRepository.delete(voucher1);
            return voucher1;
        }).orElse(null);
    }
}
