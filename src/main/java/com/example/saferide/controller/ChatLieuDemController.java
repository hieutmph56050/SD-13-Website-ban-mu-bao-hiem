package com.example.saferide.controller;

import com.example.saferide.entity.ChatLieuDem;
import com.example.saferide.service.ChatLieuDemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/chatlieudem/index")
public class ChatLieuDemController {
    @Autowired
    ChatLieuDemService service;

    @GetMapping("/list")
    public String showList(Model model) {
        model.addAttribute("list", service.getList());
        return "chatlieudem/index";
    }
    @GetMapping("/add")
    public String addForm(){
        return "chatlieudem/form";
    }
    @PostMapping("/add")
    public String addChatLieuDem(ChatLieuDem chatlieudem){
        service.add(chatlieudem);
        return "redirect:/chatlieudem/index/list";
    }
    @GetMapping("/detail/{id}")
    public String showDetail(@PathVariable("id") Integer id, Model model){
        model.addAttribute("chatlieudem",service.findById(id));
        return "chatlieudem/detail";
    }
    @GetMapping("/update/{id}")
    public String showUpdate(@PathVariable("id") Integer id,Model model){
        model.addAttribute("chatlieudem", service.findById(id));
        return "chatlieudem/update";
    }
    @PostMapping("/update")
    public String update(ChatLieuDem chatLieuDem){
        ChatLieuDem existingChatLieuDem = service.findById(chatLieuDem.getId());

        if (existingChatLieuDem != null) {
            // Cập nhật các trường cần thiết
            existingChatLieuDem.setMa(chatLieuDem.getMa());
            existingChatLieuDem.setMoTa(chatLieuDem.getMoTa());
            existingChatLieuDem.setTen(chatLieuDem.getTen());
            // Giữ nguyên ngaytao
            // Cập nhật ngaycapnhat
            existingChatLieuDem.setNgayCapNhat(LocalDateTime.now());

            service.update(existingChatLieuDem);
        }
        return "redirect:/chatlieudem/index/list";
    }
    @GetMapping("/delete")
    public String deleteChatLieuDem(@RequestParam("id") Integer id){
        service.delete(id);
        return "redirect:/chatlieudem/index/list";
    }
}