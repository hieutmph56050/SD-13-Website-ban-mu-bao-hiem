package com.example.saferide.controller;

import com.example.saferide.entity.VaiTro;
import com.example.saferide.response.ProductResponse;
import com.example.saferide.service.VaiTroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/vaitro")
public class VaiTroController {
    @Autowired
    VaiTroService service;

    @GetMapping("/danhsach")
    public ResponseEntity<?> getAll() {
        ProductResponse<VaiTro> listVaiTro = new ProductResponse<>();
        listVaiTro.data = service.getList();
        return ResponseEntity.ok(listVaiTro);
    }

    @RequestMapping("/index")
    public String showVaiTroList(Model model) {
        List<VaiTro> listVaiTro = service.getList();
        model.addAttribute("list", listVaiTro);
        return "vaitro/index";
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody VaiTro vaiTro){
        return ResponseEntity.ok(service.add(vaiTro));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody VaiTro vaiTro){
        return ResponseEntity.ok(service.update(vaiTro,id));
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