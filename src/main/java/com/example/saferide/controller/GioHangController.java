package com.example.saferide.controller;


import com.example.saferide.entity.GioHang;
import com.example.saferide.response.ProductResponse;
import com.example.saferide.service.GioHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/giohang")
public class GioHangController {
    @Autowired
    GioHangService service;

    @GetMapping("/danhsach")
    public ResponseEntity<?> getAll() {
        ProductResponse<GioHang> listGioHang = new ProductResponse<>();
        listGioHang.data = service.getList();
        return ResponseEntity.ok(listGioHang);
    }

    @RequestMapping("/index")
    public String showGioHangList(Model model) {
        List<GioHang> listGioHang = service.getList();
        model.addAttribute("list", listGioHang);
        return "giohang/index";
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody GioHang gioHang){
        return ResponseEntity.ok(service.add(gioHang));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody GioHang gioHang){
        return ResponseEntity.ok(service.update(gioHang,id));
    }
    @GetMapping("{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id){
        return ResponseEntity.ok(service.findById(id));
    }
}