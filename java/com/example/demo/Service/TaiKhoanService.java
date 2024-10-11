package com.example.demo.Service;

import com.example.demo.Entity.TaiKhoan;
import com.example.demo.Repository.TaiKhoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaiKhoanService {
    @Autowired
    TaiKhoanRepository taikhoanRepository;

    public List<TaiKhoan> getList() {
        return taikhoanRepository.findAll();
    }

    public TaiKhoan findById(Integer id) {
        return taikhoanRepository.findById(id).get();
    }

    public void delete(Integer id) {
        taikhoanRepository.deleteById(id);
    }

    public TaiKhoan add(TaiKhoan taiKhoan) {
        return taikhoanRepository.save(taiKhoan);
    }

    public TaiKhoan update(TaiKhoan taiKhoan) {
        return taikhoanRepository.save(taiKhoan);
    }
}