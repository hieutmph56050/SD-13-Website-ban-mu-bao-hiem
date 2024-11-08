package com.example.saferide.controller;


import com.example.saferide.entity.LoaiMu;
import com.example.saferide.response.ProductResponse;
import com.example.saferide.service.LoaiMuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/loaimu")
public class LoaiMuController {
    @Autowired
    LoaiMuService service;

    @GetMapping("/danhsach")
    public ResponseEntity<?> getAll() {
        ProductResponse<LoaiMu> listLoaiMu = new ProductResponse<>();
        listLoaiMu.data = service.getList();
        return ResponseEntity.ok(listLoaiMu);
    }

    @RequestMapping("/index")
    public String showLoaiMuList(Model model) {
        List<LoaiMu> listLoaiMu = service.getList();
        model.addAttribute("list", listLoaiMu);
        return "loaimu/index";
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