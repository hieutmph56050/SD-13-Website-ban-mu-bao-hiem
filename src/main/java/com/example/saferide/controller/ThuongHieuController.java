package com.example.saferide.controller;

import com.example.saferide.entity.ThuongHieu;

import com.example.saferide.service.ThuongHieuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Controller
@RequestMapping("/api/thuonghieu")
public class ThuongHieuController {
    @Autowired
    ThuongHieuService service;

    @GetMapping
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(service.getList());
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody ThuongHieu thuongHieu){
        return ResponseEntity.ok(service.add(thuongHieu));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody ThuongHieu thuongHieu){
        return ResponseEntity.ok(service.update(thuongHieu,id));
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
    public ResponseEntity<Page<ThuongHieu>> search(
            @RequestParam String keyword,
            Pageable pageable) {
        Page<ThuongHieu> result = service.search(keyword, pageable);
        return ResponseEntity.ok(result);
    }
}