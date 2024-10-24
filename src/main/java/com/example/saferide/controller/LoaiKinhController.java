package com.example.saferide.controller;

import com.example.saferide.entity.LoaiKinh;
import com.example.saferide.service.LoaiKinhService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
}