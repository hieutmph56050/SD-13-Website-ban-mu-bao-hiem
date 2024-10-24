package com.example.saferide.controller;

import com.example.saferide.entity.HoaDon;
import com.example.saferide.service.HoaDonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/hoadon")
public class HoaDonController {
    @Autowired
    HoaDonService service;

    @GetMapping
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(service.getList());
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody HoaDon hoaDon){
        return ResponseEntity.ok(service.add(hoaDon));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody HoaDon hoaDon){
        return ResponseEntity.ok(service.update(hoaDon,id));
    }

//    @DeleteMapping("/delete/{id}")
//    public ResponseEntity<?> delete(@PathVariable Integer id){
//        return ResponseEntity.ok(service.delete(id));
//    }
    @GetMapping("{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id){
        return ResponseEntity.ok(service.findById(id));
    }
}