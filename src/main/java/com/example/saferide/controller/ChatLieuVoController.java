package com.example.saferide.controller;


import com.example.saferide.entity.ChatLieuVo;
import com.example.saferide.service.ChatLieuVoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
}