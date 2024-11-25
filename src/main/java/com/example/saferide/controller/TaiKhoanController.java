
package com.example.saferide.controller;

import com.example.saferide.entity.TaiKhoan;
import com.example.saferide.response.ProductResponse;
import com.example.saferide.service.TaiKhoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/taikhoan")
public class TaiKhoanController {
    @Autowired
    TaiKhoanService service;

    @GetMapping("/danhsach")
    public ResponseEntity<?> getAll() {
        ProductResponse<TaiKhoan> listTaiKhoan = new ProductResponse<>();
        listTaiKhoan.data = service.getList();
        return ResponseEntity.ok(listTaiKhoan);
    }

    @RequestMapping("/index")
    public String showTaiKhoanList(Model model) {
        List<TaiKhoan> listTaiKhoan = service.getList();
        model.addAttribute("list", listTaiKhoan);
        return "taikhoan/index";
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody TaiKhoan taiKhoan){
        return ResponseEntity.ok(service.add(taiKhoan));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody TaiKhoan taiKhoan){
        return ResponseEntity.ok(service.update(taiKhoan,id));
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
