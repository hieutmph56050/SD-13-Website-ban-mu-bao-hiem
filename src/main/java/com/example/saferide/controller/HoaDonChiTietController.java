package com.example.saferide.controller;


import com.example.saferide.entity.HoaDon;
import com.example.saferide.entity.HoaDonChiTiet;
import com.example.saferide.entity.SPChiTiet;
import com.example.saferide.service.HoaDonChiTietService;
import com.example.saferide.service.HoaDonService;
import com.example.saferide.service.SPChiTietService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/hoadonchitiet/index")
public class HoaDonChiTietController {
    @Autowired
    HoaDonChiTietService service;

    @Autowired
    HoaDonService hoadonService;

    @Autowired
    SPChiTietService spchitietService;

    @GetMapping("/list")
    public String getList(Model model){
        model.addAttribute("list",service.getList());
        return "hoadonchitiet/index";
    }
    @GetMapping("/add")
    public String addForm() {
        return "hoadonchitiet/form";
    }
    @PostMapping("/add")
    public String add(HoaDonChiTiet hoadonchitiet) {
        service.add(hoadonchitiet);
        return "redirect:/hoadonchitiet/index/list";
    }
    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") Integer id,Model model) {
        model.addAttribute("hoadonchitiet",service.findById(id));
        return "hoadonchitiet/detail";
    }
    @GetMapping("/update/{id}")
    public String showUpdate(@PathVariable("id") Integer id, Model model){
        model.addAttribute("hoadonchitiet",service.findById(id));
        return "hoadonchitiet/update";
    }
    @PostMapping("/update")
    public String update(HoaDonChiTiet hoadonChiTiet){
        HoaDonChiTiet existingHoaDonChiTiet = service.findById(hoadonChiTiet.getId());

        if (existingHoaDonChiTiet != null) {
            // Cập nhật các trường cần thiết
            existingHoaDonChiTiet.setMa(hoadonChiTiet.getMa());
            existingHoaDonChiTiet.setIdHoaDon(hoadonChiTiet.getIdHoaDon());
            existingHoaDonChiTiet.setIdSPCT(hoadonChiTiet.getIdSPCT());
            existingHoaDonChiTiet.setTongTien(hoadonChiTiet.getTongTien());
            existingHoaDonChiTiet.setSl(hoadonChiTiet.getSl());
            existingHoaDonChiTiet.setGhiChu(hoadonChiTiet.getGhiChu());
            existingHoaDonChiTiet.setTt(hoadonChiTiet.getTt());
            // Giữ nguyên ngaytao
            // Cập nhật ngaycapnhat
            existingHoaDonChiTiet.setNgayCapNhat(LocalDateTime.now());

            service.update(existingHoaDonChiTiet);
        }
        return "redirect:/hoadonchitiet/index/list";
    }
    @GetMapping("/delete")
    public String delete(@RequestParam("id") Integer id){
        service.delete(id);
        return "redirect:/hoadonchitiet/index/list";
    }
    @ModelAttribute("listHoaDon")
    List<HoaDon> getListHoaDon() { return hoadonService.getList();
    }
    @ModelAttribute("listSPChiTiet")
    List<SPChiTiet> getListSPChiTiet() {
        return spchitietService.getList();
    }
}