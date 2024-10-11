package com.example.demo.Controller;

import com.example.demo.Entity.SanPham;
import com.example.demo.Service.SanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/sanpham/index")
public class SanPhamController {
    @Autowired
    SanPhamService service;

    @GetMapping("/list")
    public String showList(Model model) {
        model.addAttribute("list", service.getList());
        return "sanpham/index";
    }
    @GetMapping("/add")
    public String addForm(){
        return "sanpham/form";
    }
    @PostMapping("/add")
    public String addSanPham(SanPham sanpham){
        service.add(sanpham);
        return "redirect:/sanpham/index/list";
    }
    @GetMapping("/detail/{id}")
    public String showDetail(@PathVariable("id") Integer id, Model model){
        model.addAttribute("sanpham",service.findById(id));
        return "sanpham/detail";
    }
    @GetMapping("/update/{id}")
    public String showUpdate(@PathVariable("id") Integer id,Model model){
        model.addAttribute("sanpham",service.findById(id));
        return "sanpham/update";
    }
    @PostMapping("/update")
    public String updateSanPham(SanPham sanpham){
        service.update(sanpham);
        return "redirect:/sanpham/index/list";
    }
    @GetMapping("/delete")
    public String deleteSanPham(@RequestParam("id") Integer id){
        service.delete(id);
        return "redirect:/sanpham/index/list";
    }
}