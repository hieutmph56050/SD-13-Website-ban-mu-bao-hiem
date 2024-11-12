package com.example.saferide.controller;

import com.example.saferide.entity.ChatLieuDem;
import com.example.saferide.repository.ChatLieuDemRepository;
import com.example.saferide.service.ChatLieuDemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/chatlieudem")
public class ChatLieuDemController {
    @Autowired
    ChatLieuDemService service;

    @GetMapping
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(service.getList());
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody ChatLieuDem chatLieuDem){
        return ResponseEntity.ok(service.add(chatLieuDem));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody ChatLieuDem chatLieuDem){
        return ResponseEntity.ok(service.update(chatLieuDem,id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){
        return ResponseEntity.ok(service.delete(id));
    }
    @GetMapping("{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id){
        return ResponseEntity.ok(service.findById(id));
    }
    // Thêm phương thức phân trang
    @GetMapping("/phan-trang")
    public ResponseEntity<Page<ChatLieuDem>> phanTrang(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(service.getAllWithPagination(pageable));
    }
    // Phương thức tìm kiếm theo tất cả các trường
    @GetMapping("/search")
    public ResponseEntity<List<ChatLieuDem>> searchByAllFields(@RequestParam String searchTerm) {
        List<ChatLieuDem> result = service.searchByAllFields(searchTerm);
        return ResponseEntity.ok(result);
    }
}