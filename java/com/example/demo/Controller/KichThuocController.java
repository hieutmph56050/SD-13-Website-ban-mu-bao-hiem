package com.example.demo.Controller;

import com.example.demo.Entity.KichThuoc;
import com.example.demo.Service.KichThuocService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/kichthuoc/index")
public class KichThuocController {
    @Autowired
    KichThuocService service;

    @GetMapping("/list")
    public String showList(Model model) {
        model.addAttribute("list", service.getList());
        return "kichthuoc/index";
    }
    @GetMapping("/add")
    public String addForm(){
        return "kichthuoc/form";
    }
    @PostMapping("/add")
    public String addKichThuoc(KichThuoc kichthuoc){
        service.add(kichthuoc);
        return "redirect:/kichthuoc/index/list";
    }
    @GetMapping("/detail/{id}")
    public String showDetail(@PathVariable("id") Integer id, Model model){
        model.addAttribute("kichthuoc",service.findById(id));
        return "kichthuoc/detail";
    }
    @GetMapping("/update/{id}")
    public String showUpdate(@PathVariable("id") Integer id,Model model){
        model.addAttribute("kichthuoc", service.findById(id));
        return "kichthuoc/update";
    }
    @PostMapping("/update")
    public String update(KichThuoc kichThuoc){
        KichThuoc existingKichThuoc = service.findById(kichThuoc.getId());

        if (existingKichThuoc != null) {
            // Cập nhật các trường cần thiết
            existingKichThuoc.setMa(kichThuoc.getMa());
            existingKichThuoc.setMoTa(kichThuoc.getMoTa());
            existingKichThuoc.setTen(kichThuoc.getTen());
            // Giữ nguyên ngaytao
            // Cập nhật ngaycapnhat
            existingKichThuoc.setNgayCapNhat(LocalDateTime.now());

            service.update(existingKichThuoc);
        }
        return "redirect:/kichthuoc/index/list";
    }
    @GetMapping("/delete")
    public String deleteKichThuoc(@RequestParam("id") Integer id){
        service.delete(id);
        return "redirect:/kichthuoc/index/list";
    }
}