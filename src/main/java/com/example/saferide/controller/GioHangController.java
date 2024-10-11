package com.example.saferide.controller;


import com.example.saferide.entity.GioHang;
import com.example.saferide.entity.TaiKhoan;
import com.example.saferide.service.GioHangService;
import com.example.saferide.service.TaiKhoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/giohang/index")
public class GioHangController {
    @Autowired
    GioHangService service;

    @Autowired
    TaiKhoanService taikhoanservice;

    @GetMapping("/list")
    public String showList(Model model) {
        model.addAttribute("list", service.getList());
        return "giohang/index";
    }
    @GetMapping("/add")
    public String addForm(){
        return "giohang/form";
    }
    @PostMapping("/add")
    public String addGioHang(GioHang giohang){
        service.add(giohang);
        return "redirect:/giohang/index/list";
    }
    @GetMapping("/detail/{id}")
    public String showDetail(@PathVariable("id") Integer id, Model model){
        model.addAttribute("giohang",service.findById(id));
        return "giohang/detail";
    }
    @GetMapping("/update/{id}")
    public String showUpdate(@PathVariable("id") Integer id,Model model){
        model.addAttribute("giohang", service.findById(id));
        return "giohang/update";
    }
    @PostMapping("/update")
    public String update(GioHang gioHang){
        GioHang existingGioHang = service.findById(gioHang.getId());

        if (existingGioHang != null) {
            // Cập nhật các trường cần thiết
            existingGioHang.setTt(gioHang.isTt());
            // Giữ nguyên ngaytao
            // Cập nhật ngaycapnhat
            existingGioHang.setNgayCapNhat(LocalDateTime.now());

            service.update(existingGioHang);
        }
        return "redirect:/giohang/index/list";
    }
    @ModelAttribute("listTaiKhoan")
    List<TaiKhoan> getListTK(){
        return taikhoanservice.getList();
    }
}