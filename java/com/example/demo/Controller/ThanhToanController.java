package com.example.demo.Controller;

import com.example.demo.Entity.HoaDon;
import com.example.demo.Entity.ThanhToan;
import com.example.demo.Service.HoaDonService;
import com.example.demo.Service.ThanhToanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/thanhtoan/index")
public class ThanhToanController {
    @Autowired
    ThanhToanService service;

    @Autowired
    HoaDonService hoadonservice;

    @GetMapping("/list")
    public String showList(Model model) {
        model.addAttribute("list", service.getList());
        return "thanhtoan/index";
    }
    @GetMapping("/add")
    public String addForm(){
        return "thanhtoan/form";
    }
    @PostMapping("/add")
    public String addThanhToan(ThanhToan thanhtoan){
        String nguoiTao = "Admin";
        thanhtoan.setNguoiTao(nguoiTao);
        service.add(thanhtoan);
        return "redirect:/thanhtoan/index/list";
    }
    @GetMapping("/detail/{id}")
    public String showDetail(@PathVariable("id") Integer id, Model model){
        model.addAttribute("thanhtoan",service.findById(id));
        return "thanhtoan/detail";
    }
    @GetMapping("/update/{id}")
    public String showUpdate(@PathVariable("id") Integer id,Model model){
        model.addAttribute("thanhtoan", service.findById(id));
        return "thanhtoan/update";
    }
    @PostMapping("/update")
    public String update(ThanhToan thanhToan){
        ThanhToan existingThanhToan = service.findById(thanhToan.getId());

        if (existingThanhToan != null) {
            // Cập nhật các trường cần thiết
            existingThanhToan.setIdHoaDon(thanhToan.getIdHoaDon());
            existingThanhToan.setTen(thanhToan.getTen());
            existingThanhToan.setTongTien(thanhToan.getTongTien());
            existingThanhToan.setNgayThanhToan(thanhToan.getNgayThanhToan());
            existingThanhToan.setTt(thanhToan.getTt());
            existingThanhToan.setNguoiCapNhat(thanhToan.getNguoiCapNhat());
            // Giữ nguyên ngaytao
            // Cập nhật ngaycapnhat
            existingThanhToan.setNgayCapNhat(LocalDateTime.now());

            service.update(existingThanhToan);
        }
        return "redirect:/thanhtoan/index/list";
    }
    @ModelAttribute("listHoaDon")
    List<HoaDon> getListHD(){
        return hoadonservice.getList();
    }
}