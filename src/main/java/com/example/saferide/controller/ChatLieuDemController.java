package com.example.saferide.controller;

import com.example.saferide.entity.ChatLieuDem;
import com.example.saferide.response.ProductResponse;
import com.example.saferide.service.ChatLieuDemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/chatlieudem")
public class ChatLieuDemController {
    @Autowired
    ChatLieuDemService service;

    @GetMapping("/danhsach")
    public ResponseEntity<?> getAll() {
        ProductResponse<ChatLieuDem> listChatLieuDem = new ProductResponse<>();
        listChatLieuDem.data = service.getList();
        return ResponseEntity.ok(listChatLieuDem);
    }

    @RequestMapping("/index")
    public String showChatLieuDemList(Model model) {
        List<ChatLieuDem> listChatLieuDem = service.getList();
        model.addAttribute("list", listChatLieuDem);
        return "chatlieudem/index";
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
}