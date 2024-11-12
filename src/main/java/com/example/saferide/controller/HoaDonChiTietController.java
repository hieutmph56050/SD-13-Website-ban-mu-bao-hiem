package com.example.saferide.controller;

import com.example.saferide.entity.HoaDonChiTiet;
import com.example.saferide.service.HoaDonChiTietService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@Controller
@RequestMapping("/api/hoadonchitiet")
public class HoaDonChiTietController {
    @Autowired
    HoaDonChiTietService service;

    @GetMapping
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(service.getList());
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody HoaDonChiTiet hoadonChiTiet){
        return ResponseEntity.ok(service.add(hoadonChiTiet));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody HoaDonChiTiet hoadonChiTiet){
        return ResponseEntity.ok(service.update(hoadonChiTiet,id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){
        return ResponseEntity.ok(service.delete(id));
    }
    @GetMapping("{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id){
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(
            @RequestParam(required = false) String mahdct,
            @RequestParam(required = false) BigDecimal tongTien,
            @RequestParam(required = false) Integer sl,
            @RequestParam(required = false) String ghiChu,
            @RequestParam(required = false) String tt,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<HoaDonChiTiet> result = service.search(mahdct, tongTien, sl, ghiChu, tt, pageable);
        return ResponseEntity.ok(result);
    }
}