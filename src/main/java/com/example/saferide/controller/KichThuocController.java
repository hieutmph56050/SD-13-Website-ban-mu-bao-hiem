package com.example.saferide.controller;

import com.example.saferide.entity.KhuyenMai;
import com.example.saferide.entity.KichThuoc;
import com.example.saferide.response.ProductResponse;
import com.example.saferide.service.KichThuocService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Controller
@RequestMapping("/kichthuoc")
public class KichThuocController {
    @Autowired
    KichThuocService service;

    @GetMapping("/danhsach")
    public ResponseEntity<?> getAll() {
        ProductResponse<KichThuoc> listKichThuoc = new ProductResponse<>();
        listKichThuoc.data = service.getList();
        return ResponseEntity.ok(listKichThuoc);
    }

    @RequestMapping("/index")
    public String showKichThuocList(Model model) {
        List<KichThuoc> listKichThuoc = service.getList();
        model.addAttribute("list", listKichThuoc);
        return "kichthuoc/index";
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody KichThuoc kichThuoc){
        return ResponseEntity.ok(service.add(kichThuoc));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody KichThuoc kichThuoc){
        return ResponseEntity.ok(service.update(kichThuoc,id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){
        return ResponseEntity.ok(service.delete(id));
    }
    @GetMapping("{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id){
        return ResponseEntity.ok(service.findById(id));
    }
    // Tìm kiếm với phân trang
    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam(required = false) String keyword,
                                    @RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<KichThuoc> result = service.search(keyword, pageable);
        return ResponseEntity.ok(result);
    }

}