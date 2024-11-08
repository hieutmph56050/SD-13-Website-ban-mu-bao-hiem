package com.example.saferide.controller;

import com.example.saferide.entity.MauSac;
import com.example.saferide.response.ProductResponse;
import com.example.saferide.service.MauSacService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/mausac")
public class MauSacController {
    @Autowired
    MauSacService service;

    @GetMapping("/danhsach")
    public ResponseEntity<?> getAll() {
        ProductResponse<MauSac> listMauSac = new ProductResponse<>();
        listMauSac.data = service.getList();
        return ResponseEntity.ok(listMauSac);
    }

    @RequestMapping("/index")
    public String showMauSacList(Model model) {
        List<MauSac> listMauSac = service.getList();
        model.addAttribute("list", listMauSac);
        return "mausac/index";
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody MauSac mauSac){
        return ResponseEntity.ok(service.add(mauSac));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody MauSac mauSac){
        return ResponseEntity.ok(service.update(mauSac,id));
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