package com.example.demo.Controller;

import com.example.demo.Entity.*;
import com.example.demo.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/taikhoan/index")
public class TaiKhoanController {
    @Autowired
    TaiKhoanService service;

    @Autowired
    VaiTroService vaiTroService;

    @GetMapping("/list")
    public String showList(Model model) {
        model.addAttribute("list", service.getList());
        return "taikhoan/index";
    }
    @GetMapping("/add")
    public String addForm(){
        return "taikhoan/form";
    }
    @PostMapping("/add")
    public String addTaiKhoan(TaiKhoan taikhoan){
        service.add(taikhoan);
        return "redirect:/taikhoan/index/list";
    }
    @GetMapping("/detail/{id}")
    public String showDetail(@PathVariable("id") Integer id, Model model){
        model.addAttribute("taikhoan",service.findById(id));
        return "taikhoan/detail";
    }
    @GetMapping("/update/{id}")
    public String showUpdate(@PathVariable("id") Integer id,Model model){
        model.addAttribute("taikhoan", service.findById(id));
        return "taikhoan/update";
    }
    @PostMapping("/update")
    public String update(TaiKhoan spChiTiet){
        TaiKhoan existingTaiKhoan = service.findById(spChiTiet.getId());

        if (existingTaiKhoan != null) {
            // Cập nhật các trường cần thiết
            existingTaiKhoan.setIdVaiTro(spChiTiet.getIdVaiTro());
            existingTaiKhoan.setTenDangNhap(spChiTiet.getTenDangNhap());
            existingTaiKhoan.setMatKhau(spChiTiet.getMatKhau());
            existingTaiKhoan.setTen(spChiTiet.getTen());
            existingTaiKhoan.setNgaySinh(spChiTiet.getNgaySinh());
            existingTaiKhoan.setGioiTinh(spChiTiet.isGioiTinh());
            existingTaiKhoan.setSdt(spChiTiet.getSdt());
            existingTaiKhoan.setEmail(spChiTiet.getEmail());
            existingTaiKhoan.setCccd(spChiTiet.getCccd());
            existingTaiKhoan.setAvatar(spChiTiet.getAvatar());
            existingTaiKhoan.setTt(spChiTiet.isTt());
            // Giữ nguyên ngaytao
            // Cập nhật ngaycapnhat
            existingTaiKhoan.setNgayCapNhat(LocalDateTime.now());

            service.update(existingTaiKhoan);
        }
        return "redirect:/taikhoan/index/list";
    }
    @GetMapping("/delete")
    public String deleteTaiKhoan(@RequestParam("id") Integer id){
        service.delete(id);
        return "redirect:/taikhoan/index/list";
    }
    @ModelAttribute("listVaiTro")
    List<VaiTro> getListSP() {
        return vaiTroService.getList();
    }
}