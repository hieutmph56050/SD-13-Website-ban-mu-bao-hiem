package com.example.saferide.controller;


import com.example.saferide.entity.GioHang;
import com.example.saferide.service.GioHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

import java.time.LocalDateTime;

@Controller
    @RequestMapping("/api/giohang")
public class GioHangController {
    @Autowired
    GioHangService service;

    @GetMapping
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(service.getList());
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody GioHang gioHang){
        return ResponseEntity.ok(service.add(gioHang));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody GioHang gioHang){
        return ResponseEntity.ok(service.update(gioHang,id));
    }
    @GetMapping("{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id){
        return ResponseEntity.ok(service.findById(id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        service.deleteById(id);
        return ResponseEntity.ok("GioHang with ID " + id + " has been deleted.");
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(
            @RequestParam(required = false) Integer idTaiKhoan,
            @RequestParam(required = false) Boolean tt,
            @PageableDefault(size = 10, page = 0) Pageable pageable) {
        Page<GioHang> result = service.search(idTaiKhoan, tt, pageable);
        return ResponseEntity.ok(result);
    }
}