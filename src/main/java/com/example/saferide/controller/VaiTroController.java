package com.example.saferide.controller;


import com.example.saferide.entity.VaiTro;
import com.example.saferide.service.VaiTroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/vaitro/index")
public class VaiTroController {
    @Autowired
    VaiTroService service;

    @GetMapping("/list")
    public String showList(Model model) {
        model.addAttribute("list", service.getList());
        return "vaitro/index";
    }
    @GetMapping("/add")
    public String addForm(){
        return "vaitro/form";
    }
    @PostMapping("/add")
    public String addVaiTro(VaiTro vaitro){
        service.add(vaitro);
        return "redirect:/vaitro/index/list";
    }
    @GetMapping("/detail/{id}")
    public String showDetail(@PathVariable("id") Integer id, Model model){
        model.addAttribute("vaitro",service.findById(id));
        return "vaitro/detail";
    }
    @GetMapping("/update/{id}")
    public String showUpdate(@PathVariable("id") Integer id,Model model){
        model.addAttribute("vaitro", service.findById(id));
        return "vaitro/update";
    }
    @PostMapping("/update")
    public String update(VaiTro vaiTro){
        VaiTro existingVaiTro = service.findById(vaiTro.getId());

        if (existingVaiTro != null) {
            // Cập nhật các trường cần thiết
            existingVaiTro.setTen(vaiTro.getTen());
            existingVaiTro.setMoTa(vaiTro.getMoTa());
            // Giữ nguyên ngaytao
            // Cập nhật ngaycapnhat
            existingVaiTro.setNgayCapNhat(LocalDateTime.now());

            service.update(existingVaiTro);
        }
        return "redirect:/vaitro/index/list";
    }
    @GetMapping("/delete")
    public String deleteVaiTro(@RequestParam("id") Integer id){
        service.delete(id);
        return "redirect:/vaitro/index/list";
    }
}