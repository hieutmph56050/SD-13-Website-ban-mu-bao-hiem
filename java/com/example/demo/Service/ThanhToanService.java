package com.example.demo.Service;

import com.example.demo.Entity.ThanhToan;
import com.example.demo.Repository.ThanhToanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThanhToanService {
    @Autowired
    ThanhToanRepository thanhtoanRepository;

    public List<ThanhToan> getList() {
        return thanhtoanRepository.findAll();
    }

    public ThanhToan findById(Integer id) {
        return thanhtoanRepository.findById(id).get();
    }

    public void delete(Integer id) {
        thanhtoanRepository.deleteById(id);
    }

    public ThanhToan add(ThanhToan thanhToan) {
        return thanhtoanRepository.save(thanhToan);
    }

    public ThanhToan update(ThanhToan thanhToan) {
        return thanhtoanRepository.save(thanhToan);
    }
}
