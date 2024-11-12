package com.example.saferide.controller;


import com.example.saferide.entity.ChatLieuVo;
import com.example.saferide.service.ChatLieuVoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;


@Controller
@RequestMapping("/api/chatlieuvo")
public class ChatLieuVoController {
    @Autowired
    ChatLieuVoService service;

    @GetMapping
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(service.getList());
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody ChatLieuVo chatLieuVo){
        return ResponseEntity.ok(service.add(chatLieuVo));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody ChatLieuVo chatLieuVo){
        return ResponseEntity.ok(service.update(chatLieuVo,id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){
        return ResponseEntity.ok(service.delete(id));
    }
    @GetMapping("{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id){
        return ResponseEntity.ok(service.findById(id));
    }
    // Phương thức tìm kiếm theo tất cả các trường
    @GetMapping("/search")
    public ResponseEntity<List<ChatLieuVo>> searchByAllFields(@RequestParam String searchTerm) {
        List<ChatLieuVo> result = service.searchByAllFields(searchTerm);
        return ResponseEntity.ok(result);
    }

    // Phương thức phân trang
    @GetMapping("/phan-trang")
    public ResponseEntity<Page<ChatLieuVo>> getAllWithPagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ChatLieuVo> result = service.getAllWithPagination(pageable);
        return ResponseEntity.ok(result);
    }
}