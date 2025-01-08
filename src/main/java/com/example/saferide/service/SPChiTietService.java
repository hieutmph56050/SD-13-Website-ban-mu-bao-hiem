package com.example.saferide.service;

import com.example.saferide.entity.SPChiTiet;
import com.example.saferide.repository.SPChiTietRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

@Service
public class SPChiTietService {
    @Autowired
    SPChiTietRepository spchitietRepository;

    public List<SPChiTiet> getList() {
        return spchitietRepository.findAll();
    }

    public SPChiTiet findById(Integer id) {
        return spchitietRepository.findById(id).get();
    }

    public SPChiTiet add(SPChiTiet spChiTiet) {
        return spchitietRepository.save(spChiTiet);
    }

    public SPChiTiet update(SPChiTiet spChiTiet, Integer id) {
        Optional<SPChiTiet> optional = spchitietRepository.findById(id);
        return optional.map(spChiTiet1 -> {
            spChiTiet1.setIdSanPham(spChiTiet.getIdSanPham());
            spChiTiet1.setIdThuongHieu(spChiTiet.getIdThuongHieu());
            spChiTiet1.setIdChatLieuVo(spChiTiet.getIdChatLieuVo());
            spChiTiet1.setIdLoaiMu(spChiTiet.getIdLoaiMu());
            spChiTiet1.setIdKichThuoc(spChiTiet.getIdKichThuoc());
            spChiTiet1.setIdKhuyenMai(spChiTiet.getIdKhuyenMai());
            spChiTiet1.setIdLoaiKinh(spChiTiet.getIdLoaiKinh());
            spChiTiet1.setIdChatLieuDem(spChiTiet.getIdChatLieuDem());
            spChiTiet1.setIdMauSac(spChiTiet.getIdMauSac());
            spChiTiet1.setMa(spChiTiet.getMa());
            spChiTiet1.setSl(spChiTiet.getSl());
            spChiTiet1.setDonGia(spChiTiet.getDonGia());
            spChiTiet1.setAnh(spChiTiet.getAnh());
            spChiTiet1.setXuatXu(spChiTiet.getXuatXu());
            spChiTiet1.setMoTaCT(spChiTiet.getMoTaCT());
            spChiTiet1.setTt(spChiTiet.getTt());
            return spchitietRepository.save(spChiTiet1);
        }).orElse(null);
    }
    public SPChiTiet delete(Integer id) {
        Optional<SPChiTiet> optional = spchitietRepository.findById(id);
        return optional.map(spChiTiet1 -> {
            spchitietRepository.delete(spChiTiet1);
            return spChiTiet1;
        }).orElse(null);
    }

    public Page<SPChiTiet> getAllPaged(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("ngayTao").descending());
        return spchitietRepository.findAll(pageable);
    }
    public List<SPChiTiet> search(String keyword, int quantity) {
        return spchitietRepository.findByMaContainingOrMoTaCTContainingOrXuatXuContainingAndSlGreaterThanEqual(
                keyword, keyword, keyword, quantity);
    }

    public SPChiTiet getProductById(int id) {
        return spchitietRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
    }
}
