package com.example.saferide.controller;

import com.example.saferide.entity.GioHangChiTiet;
import com.example.saferide.service.GioHangChiTietService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


@Controller
@RequestMapping("/api/giohangchitiet")
public class GioHangChiTietController {
    @Autowired
    GioHangChiTietService service;

    @GetMapping
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(service.getList());
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody GioHangChiTiet giohangChiTiet){
        return ResponseEntity.ok(service.add(giohangChiTiet));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody GioHangChiTiet giohangChiTiet){
        return ResponseEntity.ok(service.update(giohangChiTiet,id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){
        return ResponseEntity.ok(service.delete(id));
    }
    @GetMapping("{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id){
        return ResponseEntity.ok(service.findById(id));
    }
    // Tìm kiếm với phân trang
    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam String searchTerm,
                                    @RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<GioHangChiTiet> result = service.searchByAllFields(searchTerm, pageable);
        return ResponseEntity.ok(result);
    }

    // Phân trang cơ bản
    @GetMapping("/pagination")
    public ResponseEntity<?> getAllWithPagination(@RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(service.getAllWithPagination(pageable));
    }
}