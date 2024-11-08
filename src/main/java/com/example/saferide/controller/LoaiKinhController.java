package com.example.saferide.controller;

import com.example.saferide.entity.LoaiKinh;
import com.example.saferide.response.ProductResponse;
import com.example.saferide.service.LoaiKinhService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/loaikinh")
public class LoaiKinhController {
    @Autowired
    LoaiKinhService service;

    @GetMapping("/danhsach")
    public ResponseEntity<?> getAll() {
        ProductResponse<LoaiKinh> listLoaiKinh = new ProductResponse<>();
        listLoaiKinh.data = service.getList();
        return ResponseEntity.ok(listLoaiKinh);
    }

    @RequestMapping("/index")
    public String showLoaiKinhList(Model model) {
        List<LoaiKinh> listLoaiKinh = service.getList();
        model.addAttribute("list", listLoaiKinh);
        return "loaikinh/index";
    }
    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody LoaiKinh loaiKinh){
        return ResponseEntity.ok(service.add(loaiKinh));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody LoaiKinh loaiKinh){
        return ResponseEntity.ok(service.update(loaiKinh,id));
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