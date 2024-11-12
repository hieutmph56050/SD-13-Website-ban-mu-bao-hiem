package com.example.saferide.controller;


import com.example.saferide.entity.LoaiMu;
import com.example.saferide.service.LoaiMuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Controller
@RequestMapping("/api/loaimu")
public class LoaiMuController {
    @Autowired
    LoaiMuService service;

    @GetMapping
    public ResponseEntity<?> getAll(
            @RequestParam(required = false) String searchTerm,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<LoaiMu> result = service.getListWithPaginationAndSearch(searchTerm, pageable);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody LoaiMu loaiMu){
        return ResponseEntity.ok(service.add(loaiMu));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody LoaiMu loaiMu){
        return ResponseEntity.ok(service.update(loaiMu,id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){
        return ResponseEntity.ok(service.delete(id));
    }
    @GetMapping("{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id){
        return ResponseEntity.ok(service.findById(id));
    }
}