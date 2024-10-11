package com.example.demo.Controller;

import com.example.demo.Entity.KhuyenMai;
import com.example.demo.Service.KhuyenMaiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/khuyenmai/index")
public class KhuyenMaiController {
    @Autowired
    KhuyenMaiService service;

    @GetMapping("/list")
    public String showList(Model model) {
        model.addAttribute("list", service.getList());
        return "khuyenmai/index";
    }
    @GetMapping("/add")
    public String addForm(){
        return "khuyenmai/form";
    }
    @PostMapping("/add")
    public String addKhuyenMai(KhuyenMai khuyenmai){
        service.add(khuyenmai);
        return "redirect:/khuyenmai/index/list";
    }
    @GetMapping("/detail/{id}")
    public String showDetail(@PathVariable("id") Integer id, Model model){
        model.addAttribute("khuyenmai",service.findById(id));
        return "khuyenmai/detail";
    }
    @GetMapping("/update/{id}")
    public String showUpdate(@PathVariable("id") Integer id,Model model){
        model.addAttribute("khuyenmai", service.findById(id));
        return "khuyenmai/update";
    }
    @PostMapping("/update")
    public String update(KhuyenMai khuyenMai){
        KhuyenMai existingKhuyenMai = service.findById(khuyenMai.getId());

        if (existingKhuyenMai != null) {
            // Cập nhật các trường cần thiết
            existingKhuyenMai.setMa(khuyenMai.getMa());
            existingKhuyenMai.setTen(khuyenMai.getTen());
            existingKhuyenMai.setGiaTri(khuyenMai.getGiaTri());
            existingKhuyenMai.setNgayKT(khuyenMai.getNgayKT());
            existingKhuyenMai.setPTKM(khuyenMai.getPTKM());
            existingKhuyenMai.setDKKM(khuyenMai.getDKKM());
            existingKhuyenMai.setTt(khuyenMai.getTt());
            existingKhuyenMai.setNguoiCapNhat(khuyenMai.getNguoiCapNhat());
            // Giữ nguyên ngaytao
            // Cập nhật ngaycapnhat
            existingKhuyenMai.setNgayCapNhat(LocalDateTime.now());

            service.update(existingKhuyenMai);
        }
        return "redirect:/khuyenmai/index/list";
    }
    @GetMapping("/delete")
    public String deleteKhuyenMai(@RequestParam("id") Integer id){
        service.delete(id);
        return "redirect:/khuyenmai/index/list";
    }
}