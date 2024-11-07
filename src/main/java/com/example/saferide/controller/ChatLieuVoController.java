package com.example.saferide.controller;


import com.example.saferide.entity.ChatLieuVo;
import com.example.saferide.response.ProductResponse;
import com.example.saferide.service.ChatLieuVoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/chatlieuvo")
public class ChatLieuVoController {
    @Autowired
    ChatLieuVoService service;

    @GetMapping("/danhsach")
    public ResponseEntity<?> getAll() {
        ProductResponse<ChatLieuVo> listChatLieuVo = new ProductResponse<>();
        listChatLieuVo.data = service.getList();
        return ResponseEntity.ok(listChatLieuVo);
    }

    @RequestMapping("/index")
    public String showChatLieuVoList(Model model) {
        List<ChatLieuVo> listChatLieuVo = service.getList();
        model.addAttribute("list", listChatLieuVo);
        return "chatlieuvo/index";
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