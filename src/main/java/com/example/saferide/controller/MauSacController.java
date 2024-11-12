package com.example.saferide.controller;

import com.example.saferide.entity.MauSac;
import com.example.saferide.service.MauSacService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/api/mausac")
public class MauSacController {
    @Autowired
    MauSacService service;

    @GetMapping
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(service.getList());
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody MauSac mauSac){
        return ResponseEntity.ok(service.add(mauSac));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody MauSac mauSac){
        return ResponseEntity.ok(service.update(mauSac,id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){
        return ResponseEntity.ok(service.delete(id));
    }
    @GetMapping("{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id){
        return ResponseEntity.ok(service.findById(id));
    }
    // Thêm endpoint tìm kiếm với phân trang
    @GetMapping("/search")
    public ResponseEntity<?> search(
            @RequestParam(value = "ma", required = false) String ma,
            @RequestParam(value = "ten", required = false) String ten,
            @RequestParam(value = "moTa", required = false) String moTa,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<MauSac> result = service.search(ma, ten, moTa, page, size);
        return ResponseEntity.ok(result);
    }
}