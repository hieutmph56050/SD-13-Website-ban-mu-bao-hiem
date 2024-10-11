package com.example.demo.Controller;

import com.example.demo.Entity.LoaiMu;
import com.example.demo.Service.LoaiMuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/loaimu/index")
public class LoaiMuController {
    @Autowired
    LoaiMuService service;

    @GetMapping("/list")
    public String showList(Model model) {
        model.addAttribute("list", service.getList());
        return "loaimu/index";
    }
    @GetMapping("/add")
    public String addForm(){
        return "loaimu/form";
    }
    @PostMapping("/add")
    public String addLoaiMu(LoaiMu loaimu){
        service.add(loaimu);
        return "redirect:/loaimu/index/list";
    }
    @GetMapping("/detail/{id}")
    public String showDetail(@PathVariable("id") Integer id, Model model){
        model.addAttribute("loaimu",service.findById(id));
        return "loaimu/detail";
    }
    @GetMapping("/update/{id}")
    public String showUpdate(@PathVariable("id") Integer id,Model model){
        model.addAttribute("loaimu", service.findById(id));
        return "loaimu/update";
    }
    @PostMapping("/update")
    public String update(LoaiMu loaiMu){
        LoaiMu existingLoaiMu = service.findById(loaiMu.getId());

        if (existingLoaiMu != null) {
            // Cập nhật các trường cần thiết
            existingLoaiMu.setMa(loaiMu.getMa());
            existingLoaiMu.setMoTa(loaiMu.getMoTa());
            existingLoaiMu.setTen(loaiMu.getTen());
            // Giữ nguyên ngaytao
            // Cập nhật ngaycapnhat
            existingLoaiMu.setNgayCapNhat(LocalDateTime.now());

            service.update(existingLoaiMu);
        }
        return "redirect:/loaimu/index/list";
    }
    @GetMapping("/delete")
    public String deleteLoaiMu(@RequestParam("id") Integer id){
        service.delete(id);
        return "redirect:/loaimu/index/list";
    }
}