package com.example.saferide.controller;

import com.example.saferide.entity.Voucher;
import com.example.saferide.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/voucher")
public class VoucherController {
    @Autowired
    VoucherService service;

    @GetMapping
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(service.getList());
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody Voucher voucher){
        return ResponseEntity.ok(service.add(voucher));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody Voucher voucher){
        return ResponseEntity.ok(service.update(voucher,id));
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