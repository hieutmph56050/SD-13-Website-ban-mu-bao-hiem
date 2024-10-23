package com.example.saferide.controller;


import com.example.saferide.entity.MauSac;
import com.example.saferide.service.MauSacService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/mausac/index")
public class MauSacController {
    @Autowired
    MauSacService service;

    @GetMapping("/list")
    public String showList(Model model) {
        model.addAttribute("list", service.getList());
        return "mausac/index";
    }
    @GetMapping("/add")
    public String addForm(){
        return "mausac/form";
    }
    @PostMapping("/add")
    public String addMauSac(MauSac mausac){
        service.add(mausac);
        return "redirect:/mausac/index/list";
    }
    @GetMapping("/detail/{id}")
    public String showDetail(@PathVariable("id") Integer id, Model model){
        model.addAttribute("mausac",service.findById(id));
        return "mausac/detail";
    }
    @GetMapping("/update/{id}")
    public String showUpdate(@PathVariable("id") Integer id,Model model){
        model.addAttribute("mausac", service.findById(id));
        return "mausac/update";
    }
    @PostMapping("/update")
    public String update(MauSac mauSac){
        MauSac existingMauSac = service.findById(mauSac.getId());

        if (existingMauSac != null) {
            // Cập nhật các trường cần thiết
            existingMauSac.setMa(mauSac.getMa());
            existingMauSac.setMoTa(mauSac.getMoTa());
            existingMauSac.setTen(mauSac.getTen());
            // Giữ nguyên ngaytao
            // Cập nhật ngaycapnhat
            existingMauSac.setNgayCapNhat(LocalDateTime.now());

            service.update(existingMauSac);
        }
        return "redirect:/mausac/index/list";
    }
    @GetMapping("/delete")
    public String deleteMauSac(@RequestParam("id") Integer id){
        service.delete(id);
        return "redirect:/mausac/index/list";
    }
}