package com.example.saferide.controller;

import com.example.saferide.entity.ThuongHieu;
import com.example.saferide.entity.VaiTro;
import com.example.saferide.service.VaiTroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/vaitro")
public class VaiTroController {
    @Autowired
    VaiTroService service;

    @GetMapping
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(service.getList());
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

    @GetMapping("/search")
    public ResponseEntity<Page<VaiTro>> search(
            @RequestParam String keyword, Pageable pageable) {
        Page<VaiTro> result = service.search(keyword, pageable);
        return ResponseEntity.ok(result);
    }

}