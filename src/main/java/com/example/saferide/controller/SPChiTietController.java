package com.example.saferide.controller;

import com.example.saferide.entity.SPChiTiet;
import com.example.saferide.response.OneProductResponse;
import com.example.saferide.response.ProductResponse;
import com.example.saferide.service.SPChiTietService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/spchitiet")
public class SPChiTietController {
    @Autowired
    SPChiTietService service;


    @GetMapping("/danhsach")
    public ResponseEntity<?> getAll() {
        ProductResponse<SPChiTiet> listSPChiTiet = new ProductResponse<>();
        listSPChiTiet.data = service.getList();
        return ResponseEntity.ok(listSPChiTiet);
    }

    @RequestMapping("/index")
    public String showSPChiTietList(Model model) {
        List<SPChiTiet> listSPChiTiet = service.getList();
        model.addAttribute("list", listSPChiTiet);
        return "spchitiet/index";
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody SPChiTiet spChiTiet) {
        if (spChiTiet.getGiaGiam() != null) {
            spChiTiet.setGiaGiam(spChiTiet.getGiaGiam());
        } else {
            spChiTiet.setGiaGiam(new BigDecimal(0));
        }
        service.add(spChiTiet);
        return ResponseEntity.ok(Collections.singletonMap("data", "Thanh cong"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody SPChiTiet spChiTiet) {
        service.update(spChiTiet, id);
        return ResponseEntity.ok(Collections.singletonMap("data", "Thanh cong"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        return ResponseEntity.ok(service.delete(id));
    }

    @GetMapping("{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id) {
        OneProductResponse<SPChiTiet> sp = new OneProductResponse<>();
        sp.data = service.findById(id);
        return ResponseEntity.ok(sp);
    }
}