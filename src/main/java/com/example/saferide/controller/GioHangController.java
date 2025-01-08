package com.example.saferide.controller;


import com.example.saferide.entity.GioHang;
import com.example.saferide.entity.SPChiTiet;
import com.example.saferide.response.ProductResponse;
import com.example.saferide.service.GioHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/gio-hang")
public class GioHangController {
    @Autowired
    GioHangService service;

    @GetMapping("/danh-sach")
    public ResponseEntity<?> getAll() {
        ProductResponse<GioHang> listGioHang = new ProductResponse<>();
        listGioHang.data = service.getList();
        return ResponseEntity.ok(listGioHang);
    }
    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody GioHang gioHang) {
        return ResponseEntity.ok(service.add(gioHang));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody GioHang gioHang) {
        return ResponseEntity.ok(service.update(gioHang, id));
    }

    @GetMapping("{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id) {
        try {
            var gioHang = service.findById(id);

            if (gioHang == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Giỏ hàng không tồn tại.");
            }

            if (gioHang.getGioHangChiTietList() == null) {
                return ResponseEntity.ok(Collections.singletonMap("data", Collections.emptyList()));
            }

            List<SPChiTiet> spChiTiet = gioHang.getGioHangChiTietList().stream().map(gioHangChiTiet -> {
                if (gioHangChiTiet.getIdSPCT() == null || gioHangChiTiet.getIdSPCT().getIdSanPham() == null) {
                    throw new IllegalStateException("Thông tin sản phẩm không đầy đủ.");
                }
                SPChiTiet dto = new SPChiTiet();
                dto.setId(gioHangChiTiet.getIdSPCT().getId());
                dto.setIdSanPham(gioHangChiTiet.getIdSPCT().getIdSanPham());
                dto.setSl(gioHangChiTiet.getSl());
                dto.setDonGia(gioHangChiTiet.getIdSPCT().getDonGia());
                dto.setIdKichThuoc(gioHangChiTiet.getIdSPCT().getIdKichThuoc());
                dto.setIdMauSac(gioHangChiTiet.getIdSPCT().getIdMauSac());
                dto.setAnh(gioHangChiTiet.getIdSPCT().getAnh());
                return dto;
            }).toList();

            return ResponseEntity.ok(Collections.singletonMap("data", spChiTiet));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("error", "Có lỗi xảy ra khi xử lý yêu cầu."));
        }
    }
}