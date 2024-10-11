package com.example.saferide.controller;


import com.example.saferide.entity.LoaiKinh;
import com.example.saferide.service.LoaiKinhService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/loaikinh/index")
public class LoaiKinhController {
    @Autowired
    LoaiKinhService service;

    @GetMapping("/list")
    public String showList(Model model) {
        model.addAttribute("list", service.getList());
        return "loaikinh/index";
    }
    @GetMapping("/add")
    public String addForm(){
        return "loaikinh/form";
    }
    @PostMapping("/add")
    public String addLoaiKinh(LoaiKinh loaikinh){
        service.add(loaikinh);
        return "redirect:/loaikinh/index/list";
    }
    @GetMapping("/detail/{id}")
    public String showDetail(@PathVariable("id") Integer id, Model model){
        model.addAttribute("loaikinh",service.findById(id));
        return "loaikinh/detail";
    }
    @GetMapping("/update/{id}")
    public String showUpdate(@PathVariable("id") Integer id,Model model){
        model.addAttribute("loaikinh", service.findById(id));
        return "loaikinh/update";
    }
    @PostMapping("/update")
    public String update(LoaiKinh loaiKinh){
        LoaiKinh existingLoaiKinh = service.findById(loaiKinh.getId());

        if (existingLoaiKinh != null) {
            // Cập nhật các trường cần thiết
            existingLoaiKinh.setMa(loaiKinh.getMa());
            existingLoaiKinh.setMoTa(loaiKinh.getMoTa());
            existingLoaiKinh.setTen(loaiKinh.getTen());
            // Giữ nguyên ngaytao
            // Cập nhật ngaycapnhat
            existingLoaiKinh.setNgayCapNhat(LocalDateTime.now());

            service.update(existingLoaiKinh);
        }
        return "redirect:/loaikinh/index/list";
    }
    @GetMapping("/delete")
    public String deleteLoaiKinh(@RequestParam("id") Integer id){
        service.delete(id);
        return "redirect:/loaikinh/index/list";
    }
}