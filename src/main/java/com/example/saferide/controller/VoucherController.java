package com.example.saferide.controller;

import com.example.saferide.entity.Voucher;
import com.example.saferide.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

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

    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam(required = false) String keyword) {
        List<Voucher> result = service.searchVoucher(keyword);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/paged")
    public ResponseEntity<Page<Voucher>> getPagedVoucher(Pageable pageable) {
        Page<Voucher> result = service.getListWithPagination(pageable);
        return ResponseEntity.ok(result);
    }


}