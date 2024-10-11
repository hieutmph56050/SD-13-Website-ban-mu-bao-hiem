package com.example.saferide.controller;


import com.example.saferide.entity.ChatLieuVo;
import com.example.saferide.service.ChatLieuVoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/chatlieuvo/index")
public class ChatLieuVoController {
    @Autowired
    ChatLieuVoService service;

    @GetMapping("/list")
    public String showList(Model model) {
        model.addAttribute("list", service.getList());
        return "chatlieuvo/index";
    }
    @GetMapping("/add")
    public String addForm(){
        return "chatlieuvo/form";
    }
    @PostMapping("/add")
    public String addChatLieuVo(ChatLieuVo chatlieuvo){
        service.add(chatlieuvo);
        return "redirect:/chatlieuvo/index/list";
    }
    @GetMapping("/detail/{id}")
    public String showDetail(@PathVariable("id") Integer id, Model model){
        model.addAttribute("chatlieuvo",service.findById(id));
        return "chatlieuvo/detail";
    }
    @GetMapping("/update/{id}")
    public String showUpdate(@PathVariable("id") Integer id,Model model){
        model.addAttribute("chatlieuvo", service.findById(id));
        return "chatlieuvo/update";
    }
    @PostMapping("/update")
    public String update(ChatLieuVo chatLieuVo){
        ChatLieuVo existingChatLieuVo = service.findById(chatLieuVo.getId());

        if (existingChatLieuVo != null) {
            // Cập nhật các trường cần thiết
            existingChatLieuVo.setMa(chatLieuVo.getMa());
            existingChatLieuVo.setMoTa(chatLieuVo.getMoTa());
            existingChatLieuVo.setTen(chatLieuVo.getTen());
            // Giữ nguyên ngaytao
            // Cập nhật ngaycapnhat
            existingChatLieuVo.setNgayCapNhat(LocalDateTime.now());

            service.update(existingChatLieuVo);
        }
        return "redirect:/chatlieuvo/index/list";
    }
    @GetMapping("/delete")
    public String deleteChatLieuVo(@RequestParam("id") Integer id){
        service.delete(id);
        return "redirect:/chatlieuvo/index/list";
    }
}