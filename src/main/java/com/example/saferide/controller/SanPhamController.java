package com.example.saferide.controller;

import com.example.saferide.entity.HoaDon;
import com.example.saferide.entity.SPChiTiet;
import com.example.saferide.entity.SanPham;
import com.example.saferide.repository.SPChiTietRepository;
import com.example.saferide.response.*;
import com.example.saferide.service.SPChiTietService;
import com.example.saferide.service.SanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/sanpham")
public class SanPhamController {

    @Autowired
    SanPhamService service;

    @Autowired
    SPChiTietService CTService;

    @Autowired
    SPChiTietRepository spctRepo;

    @GetMapping("/danhsach")
    public ResponseEntity<?> getAll() {
        ProductResponse<SanPham> listSanPham = new ProductResponse<>();
        listSanPham.data = service.getList();
        return ResponseEntity.ok(listSanPham);
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody SanPham sanPham) {
        return ResponseEntity.ok(service.add(sanPham));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody SanPham sanPham) {
        SanPham existingSanPham = service.findById(id);
        if (existingSanPham == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Sản phẩm không tồn tại");
        }
        SanPham updatedSanPham = service.update(sanPham, id);
        return ResponseEntity.ok(updatedSanPham);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        return ResponseEntity.ok(service.delete(id));
    }

    @GetMapping("/dsct")
    public ResponseEntity<?> findAllProductDetails() {
        List<SanPham> sanPhamList = service.getList();
        List<ListSanPhamResponse.SanPhamRes> responseList = new ArrayList<>();
        for (SanPham sanPham : sanPhamList) {
            if (sanPham != null) {
                ListSanPhamResponse.SanPhamRes dataRes = new ListSanPhamResponse.SanPhamRes();
                dataRes.setSanPham(sanPham);
                dataRes.setChiTietList(spctRepo.findByIDSP(sanPham.getId()));
                responseList.add(dataRes);
            }
        }

        ListSanPhamResponse listData = new ListSanPhamResponse();
        listData.setData(responseList);
        return ResponseEntity.ok(listData);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id) {
        SanPham sanPham = service.findById(id);
        SanPhamResponse.SanPhamRes dataRes = new SanPhamResponse.SanPhamRes();
        dataRes.setSanPham(sanPham);
        dataRes.setChiTietList(spctRepo.findByIDSP(sanPham.getId()));

        SanPhamResponse resSP = new SanPhamResponse();
        resSP.setData(dataRes);
        return ResponseEntity.ok(resSP);
    }
    @GetMapping("/search")
    public ResponseEntity<?> search(
            @RequestParam String keyword,
            @RequestParam int page,
            @RequestParam int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<SanPham> result = service.search(keyword, pageable);
        return ResponseEntity.ok(result);
    }
    @GetMapping("/page")
    public ResponseEntity<?> getAllPaged(@RequestParam int page, @RequestParam int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<SanPham> result = service.getAllPaged(pageable);
        return ResponseEntity.ok(result);
    }
}