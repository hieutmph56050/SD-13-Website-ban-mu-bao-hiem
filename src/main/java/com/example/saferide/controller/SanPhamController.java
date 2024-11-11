package com.example.saferide.controller;

import com.example.saferide.entity.SanPham;
import com.example.saferide.response.ProductResponse;
import com.example.saferide.service.SanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sanpham")
public class SanPhamController {

    @Autowired
    SanPhamService service;

    @GetMapping("/danhsach")
    public ResponseEntity<?> getAll() {
        ProductResponse<SanPham> listSanPham = new ProductResponse<>();
        listSanPham.data = service.getList();
        return ResponseEntity.ok(listSanPham);
    }

    @RequestMapping("/index")
    public String showProductList(Model model) {
        List<SanPham> listSanPham = service.getList();
        model.addAttribute("list", listSanPham);
        return "sanpham/index";
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody SanPham sanPham) {
        return ResponseEntity.ok(service.add(sanPham));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody SanPham sanPham) {
        return ResponseEntity.ok(service.update(sanPham, id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        return ResponseEntity.ok(service.delete(id));
    }

    @GetMapping("{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }
}