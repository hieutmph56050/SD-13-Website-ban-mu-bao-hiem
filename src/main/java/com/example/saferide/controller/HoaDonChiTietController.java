package com.example.saferide.controller;

import com.example.saferide.entity.HoaDonChiTiet;
import com.example.saferide.response.ProductResponse;
import com.example.saferide.service.HoaDonChiTietService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/hoadonchitiet")
public class HoaDonChiTietController {
    @Autowired
    HoaDonChiTietService service;

    @GetMapping("/danhsach")
    public ResponseEntity<?> getAll() {
        ProductResponse<HoaDonChiTiet> listHoaDonChiTiet = new ProductResponse<>();
        listHoaDonChiTiet.data = service.getList();
        return ResponseEntity.ok(listHoaDonChiTiet);
    }

    @RequestMapping("/index")
    public String showHDCTList(Model model) {
        List<HoaDonChiTiet> listHoaDonChiTiet = service.getList();
        model.addAttribute("list", listHoaDonChiTiet);
        return "hoadonchitiet/index";
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody HoaDonChiTiet hoadonChiTiet){
        return ResponseEntity.ok(service.add(hoadonChiTiet));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody HoaDonChiTiet hoadonChiTiet){
        return ResponseEntity.ok(service.update(hoadonChiTiet,id));
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