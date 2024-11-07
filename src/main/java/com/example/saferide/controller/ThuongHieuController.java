package com.example.saferide.controller;

import com.example.saferide.entity.ThuongHieu;

import com.example.saferide.response.ProductResponse;
import com.example.saferide.service.ThuongHieuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/thuonghieu")
public class ThuongHieuController {
    @Autowired
    ThuongHieuService service;

    @GetMapping("/danhsach")
    public ResponseEntity<?> getAll() {
        ProductResponse<ThuongHieu> listThuongHieu = new ProductResponse<>();
        listThuongHieu.data = service.getList();
        return ResponseEntity.ok(listThuongHieu);
    }

    @RequestMapping("/index")
    public String showThuongHieuList(Model model) {
        List<ThuongHieu> listThuongHieu = service.getList();
        model.addAttribute("list", listThuongHieu);
        return "thuonghieu/index";
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody ThuongHieu thuongHieu){
        return ResponseEntity.ok(service.add(thuongHieu));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody ThuongHieu thuongHieu){
        return ResponseEntity.ok(service.update(thuongHieu,id));
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