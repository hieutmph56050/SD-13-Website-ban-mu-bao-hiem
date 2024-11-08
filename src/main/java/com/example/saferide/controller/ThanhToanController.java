package com.example.saferide.controller;

import com.example.saferide.entity.ThanhToan;
import com.example.saferide.response.ProductResponse;
import com.example.saferide.service.ThanhToanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/thanhtoan")
public class ThanhToanController {
    @Autowired
    ThanhToanService service;

    @GetMapping("/danhsach")
    public ResponseEntity<?> getAll() {
        ProductResponse<ThanhToan> listThanhToan = new ProductResponse<>();
        listThanhToan.data = service.getList();
        return ResponseEntity.ok(listThanhToan);
    }

    @RequestMapping("/index")
    public String showThanhToanList(Model model) {
        List<ThanhToan> listThanhToan = service.getList();
        model.addAttribute("list", listThanhToan);
        return "thanhtoan/index";
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody ThanhToan thanhToan){
        return ResponseEntity.ok(service.add(thanhToan));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody ThanhToan thanhToan){
        return ResponseEntity.ok(service.update(thanhToan,id));
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