package com.example.saferide.controller;

import com.example.saferide.entity.HoaDon;
import com.example.saferide.repository.HoaDonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/hoa-don")
public class HoaDonController {
    @Autowired
    HoaDonRepository hdRepository;


    @GetMapping("/danh-sach")
    public ResponseEntity<?> getList() {
        return ResponseEntity.ok(hdRepository.findAll());
    }

//
//    @PostMapping("/add")
//    public ResponseEntity<?> add(@RequestBody HoaDon hoaDon){
//        return ResponseEntity.ok(hdRepository.add(hoaDon));
//    }
//
//    @PutMapping("/update/{id}")
//    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody HoaDon hoaDon){
//        return ResponseEntity.ok(hdRepository.update(hoaDon,id));
//    }
//
//    @GetMapping("{id}")
//    public ResponseEntity<?> findById(@PathVariable Integer id){
//        return ResponseEntity.ok(service.findById(id));
//    }
}