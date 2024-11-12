package com.example.saferide.controller;

import com.example.saferide.entity.TaiKhoan;
import com.example.saferide.service.TaiKhoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Controller
@RequestMapping("/api/taikhoan")
public class TaiKhoanController {
    @Autowired
    TaiKhoanService service;

    @GetMapping
    public ResponseEntity<?> getAll(Pageable pageable) {
        return ResponseEntity.ok(service.getList(pageable));
    }


    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody TaiKhoan taiKhoan){
        return ResponseEntity.ok(service.add(taiKhoan));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody TaiKhoan taiKhoan){
        return ResponseEntity.ok(service.update(taiKhoan,id));
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
    public ResponseEntity<Page<TaiKhoan>> search(@RequestParam String keyword, Pageable pageable) {
        Page<TaiKhoan> result = service.search(keyword, pageable);
        return ResponseEntity.ok(result);
    }

}