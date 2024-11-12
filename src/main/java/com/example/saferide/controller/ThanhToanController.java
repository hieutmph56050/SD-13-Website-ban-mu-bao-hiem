package com.example.saferide.controller;

import com.example.saferide.entity.ThanhToan;
import com.example.saferide.service.ThanhToanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@Controller
@RequestMapping("/api/thanhtoan")
public class ThanhToanController {
    @Autowired
    ThanhToanService service;

    @GetMapping
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(service.getList());
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody ThanhToan thanhToan){
        return ResponseEntity.ok(service.add(thanhToan));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody ThanhToan thanhToan){
        return ResponseEntity.ok(service.update(thanhToan,id));
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
            @RequestParam String keyword, Pageable pageable) {
        Page<ThanhToan> result = service.search(keyword, pageable);
        return ResponseEntity.ok(result);
    }
}