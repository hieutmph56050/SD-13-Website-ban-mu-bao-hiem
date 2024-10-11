package com.example.demo.Controller;

import com.example.demo.Entity.ThuongHieu;
import com.example.demo.Service.ThuongHieuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/thuonghieu/index")
public class ThuongHieuController {
    @Autowired
    ThuongHieuService service;

    @GetMapping("/list")
    public String showList(Model model) {
        model.addAttribute("list", service.getList());
        return "thuonghieu/index";
    }
    @GetMapping("/add")
    public String addForm(){
        return "thuonghieu/form";
    }
    @PostMapping("/add")
    public String addThuongHieu(ThuongHieu thuonghieu){
        service.add(thuonghieu);
        return "redirect:/thuonghieu/index/list";
    }
    @GetMapping("/detail/{id}")
    public String showDetail(@PathVariable("id") Integer id, Model model){
        model.addAttribute("thuonghieu",service.findById(id));
        return "thuonghieu/detail";
    }
    @GetMapping("/update/{id}")
    public String showUpdate(@PathVariable("id") Integer id,Model model){
        model.addAttribute("thuonghieu", service.findById(id));
        return "thuonghieu/update";
    }
    @PostMapping("/update")
    public String update(ThuongHieu thuongHieu){
        ThuongHieu existingThuongHieu = service.findById(thuongHieu.getId());

        if (existingThuongHieu != null) {
            // Cập nhật các trường cần thiết
            existingThuongHieu.setMoTa(thuongHieu.getMoTa());
            existingThuongHieu.setTen(thuongHieu.getTen());
            // Giữ nguyên ngaytao
            // Cập nhật ngaycapnhat
            existingThuongHieu.setNgayCapNhat(LocalDateTime.now());

            service.update(existingThuongHieu);
        }
        return "redirect:/thuonghieu/index/list";
    }
    @GetMapping("/delete")
    public String deleteThuongHieu(@RequestParam("id") Integer id){
        service.delete(id);
        return "redirect:/thuonghieu/index/list";
    }
}