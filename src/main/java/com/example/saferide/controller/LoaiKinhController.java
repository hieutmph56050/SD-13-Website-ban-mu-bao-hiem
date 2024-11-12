package com.example.saferide.controller;

import com.example.saferide.entity.LoaiKinh;
import com.example.saferide.service.LoaiKinhService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Controller
@RequestMapping("/api/loaikinh")
public class LoaiKinhController {
    @Autowired
    LoaiKinhService service;

    @GetMapping
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(service.getList());
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody LoaiKinh loaiKinh){
        return ResponseEntity.ok(service.add(loaiKinh));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody LoaiKinh loaiKinh){
        return ResponseEntity.ok(service.update(loaiKinh,id));
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
    public ResponseEntity<Page<LoaiKinh>> search(
            @RequestParam(required = false) String ma,
            @RequestParam(required = false) String ten,
            @RequestParam(required = false) String moTa,
            @RequestParam(required = false) String nguoiTao,
            @RequestParam(required = false) String nguoiCapNhat,
            @RequestParam(required = false) String tt,
            Pageable pageable
    ) {
        return ResponseEntity.ok(service.search(ma, ten, moTa, nguoiTao, nguoiCapNhat, tt, pageable));
    }
}