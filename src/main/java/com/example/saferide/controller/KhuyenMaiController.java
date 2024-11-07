package com.example.saferide.controller;

import com.example.saferide.entity.KhuyenMai;
import com.example.saferide.response.ProductResponse;
import com.example.saferide.service.KhuyenMaiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/khuyenmai")
public class KhuyenMaiController {
    @Autowired
    KhuyenMaiService service;

    @GetMapping("/danhsach")
    public ResponseEntity<?> getAll() {
        ProductResponse<KhuyenMai> listKhuyenMai = new ProductResponse<>();
        listKhuyenMai.data = service.getList();
        return ResponseEntity.ok(listKhuyenMai);
    }

    @RequestMapping("/index")
    public String showKhuyenMaiList(Model model) {
        List<KhuyenMai> listKhuyenMai = service.getList();
        model.addAttribute("list", listKhuyenMai);
        return "khuyenmai/index";
    }
    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody KhuyenMai khuyenMai){
        return ResponseEntity.ok(service.add(khuyenMai));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody KhuyenMai khuyenMai){
        return ResponseEntity.ok(service.update(khuyenMai,id));
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