package com.example.saferide.controller;

import com.example.saferide.entity.Voucher;
import com.example.saferide.response.ProductResponse;
import com.example.saferide.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/voucher")
public class VoucherController {
    @Autowired
    VoucherService service;

    @GetMapping("/danhsach")
    public ResponseEntity<?> getAll() {
        ProductResponse<Voucher> listVoucher = new ProductResponse<>();
        listVoucher.data = service.getList();
        return ResponseEntity.ok(listVoucher);
    }

    @RequestMapping("/index")
    public String showVoucherList(Model model) {
        List<Voucher> listVoucher = service.getList();
        model.addAttribute("list", listVoucher);
        return "voucher/index";
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