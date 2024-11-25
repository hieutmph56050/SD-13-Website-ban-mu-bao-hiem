package com.example.saferide.service;

import com.example.saferide.entity.TaiKhoan;
import com.example.saferide.repository.TaiKhoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TaiKhoanService {
    @Autowired
    TaiKhoanRepository taikhoanRepository;

    public Page<TaiKhoan> getList(Pageable pageable) {
        return taikhoanRepository.findAll(pageable);
    }

    public TaiKhoan findById(Integer id) {
        return taikhoanRepository.findById(id).get();
    }

    public TaiKhoan add(TaiKhoan taiKhoan) {
        return taikhoanRepository.save(taiKhoan);
    }

    public TaiKhoan update(TaiKhoan taiKhoan, Integer id) {
        Optional<TaiKhoan> optional = taikhoanRepository.findById(id);
        return optional.map(taiKhoan1 -> {
            taiKhoan1.setIdVaiTro(taiKhoan.getIdVaiTro());
            taiKhoan1.setTenDangNhap(taiKhoan.getTenDangNhap());
            taiKhoan1.setMatKhau(taiKhoan.getMatKhau());
            taiKhoan1.setTen(taiKhoan.getTen());
            taiKhoan1.setNgaySinh(taiKhoan.getNgaySinh());
            taiKhoan1.setGioiTinh(taiKhoan.isGioiTinh());
            taiKhoan1.setSdt(taiKhoan.getSdt());
            taiKhoan1.setEmail(taiKhoan.getEmail());
            taiKhoan1.setCccd(taiKhoan.getCccd());
            taiKhoan1.setAvatar(taiKhoan.getAvatar());
            taiKhoan1.setTt(taiKhoan.isTt());
            taiKhoan1.setNgayCapNhat(LocalDateTime.now());
            return taikhoanRepository.save(taiKhoan1);
        }).orElse(null);
    }
    public TaiKhoan delete(Integer id) {
        Optional<TaiKhoan> optional = taikhoanRepository.findById(id);
        return optional.map(taiKhoan1 -> {
            taikhoanRepository.delete(taiKhoan1);
            return taiKhoan1;
        }).orElse(null);
    }
    public Page<TaiKhoan> search(String keyword, Pageable pageable) {
        return taikhoanRepository.searchByKeyword(keyword, pageable);
    }

}