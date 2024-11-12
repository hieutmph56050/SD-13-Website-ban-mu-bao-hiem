package com.example.saferide.controller;

import com.example.saferide.entity.*;
import com.example.saferide.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;


@RestController
@RequestMapping("/api/spchitiet")
public class SPChiTietController {
    @Autowired
    SPChiTietService service;

    @GetMapping
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(service.getList());
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody SPChiTiet spChiTiet){
        return ResponseEntity.ok(service.add(spChiTiet));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody SPChiTiet spChiTiet){
        return ResponseEntity.ok(service.update(spChiTiet,id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){
        return ResponseEntity.ok(service.delete(id));
    }
    @GetMapping("{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id){
        return ResponseEntity.ok(service.findById(id));
    }


    @GetMapping("/paged")
    public ResponseEntity<?> getPaged(
            @RequestParam int page,
            @RequestParam int size) {
        return ResponseEntity.ok(service.getAllPaged(page, size));
    }
    @GetMapping("/search")
    public ResponseEntity<?> searchByKeywordAndQuantity(
            @RequestParam String keyword,
            @RequestParam int quantity) {
        return ResponseEntity.ok(service.search(keyword, quantity));
    }
}