package com.example.demo.Controller;

import com.example.demo.Entity.GioHang;
import com.example.demo.Entity.GioHangChiTiet;
import com.example.demo.Entity.SPChiTiet;
import com.example.demo.Service.GioHangChiTietService;
import com.example.demo.Service.GioHangService;
import com.example.demo.Service.SPChiTietService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/giohangchitiet/index")
public class GioHangChiTietController {
    @Autowired
    GioHangChiTietService service;

    @Autowired
    GioHangService giohangService;

    @Autowired
    SPChiTietService spchitietService;

    @GetMapping("/list")
    public String getList(Model model){
        model.addAttribute("list",service.getList());
        return "giohangchitiet/index";
    }
    @GetMapping("/add")
    public String addForm() {
        return "giohangchitiet/form";
    }
    @PostMapping("/add")
    public String add(GioHangChiTiet giohangchitiet) {
        service.add(giohangchitiet);
        return "redirect:/giohangchitiet/index/list";
    }
    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") Integer id,Model model) {
        model.addAttribute("giohangchitiet",service.findById(id));
        return "giohangchitiet/detail";
    }
    @GetMapping("/update/{id}")
    public String showUpdate(@PathVariable("id") Integer id, Model model){
        model.addAttribute("giohangchitiet",service.findById(id));
        return "giohangchitiet/update";
    }
    @PostMapping("/update")
    public String update(GioHangChiTiet giohangChiTiet){
        GioHangChiTiet existingGioHangChiTiet = service.findById(giohangChiTiet.getId());

        if (existingGioHangChiTiet != null) {
            // Cập nhật các trường cần thiết
            existingGioHangChiTiet.setMa(giohangChiTiet.getMa());
            existingGioHangChiTiet.setIdGioHang(giohangChiTiet.getIdGioHang());
            existingGioHangChiTiet.setIdSPCT(giohangChiTiet.getIdSPCT());
            existingGioHangChiTiet.setDonGia(giohangChiTiet.getDonGia());
            existingGioHangChiTiet.setSl(giohangChiTiet.getSl());
            existingGioHangChiTiet.setTt(giohangChiTiet.getTt());
            // Giữ nguyên ngaytao
            // Cập nhật ngaycapnhat
            existingGioHangChiTiet.setNgayCapNhat(LocalDateTime.now());

            service.update(existingGioHangChiTiet);
        }
        return "redirect:/giohangchitiet/index/list";
    }
    @GetMapping("/delete")
    public String delete(@RequestParam("id") Integer id){
        service.delete(id);
        return "redirect:/giohangchitiet/index/list";
    }
    @ModelAttribute("listGioHang")
    List<GioHang> getListGioHang() { return giohangService.getList();
    }
    @ModelAttribute("listSPChiTiet")
    List<SPChiTiet> getListSPChiTiet() {
        return spchitietService.getList();
    }
}