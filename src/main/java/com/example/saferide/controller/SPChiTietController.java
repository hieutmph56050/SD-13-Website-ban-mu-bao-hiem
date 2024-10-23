package com.example.saferide.controller;


import com.example.saferide.entity.*;
import com.example.saferide.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


@Controller
@RequestMapping("/spchitiet/index")
public class SPChiTietController {
    @Autowired
    SPChiTietService service;

    @Autowired
    ChatLieuDemService chatLieuDemService;

    @Autowired
    ChatLieuVoService chatLieuVoService;

    @Autowired
    KhuyenMaiService khuyenMaiService;

    @Autowired
    KichThuocService kichThuocService;

    @Autowired
    LoaiKinhService loaiKinhService;

    @Autowired
    LoaiMuService loaiMuService;

    @Autowired
    MauSacService mauSacService;

    @Autowired
    SanPhamService sanPhamService;

    @Autowired
    ThuongHieuService thuongHieuService;

    @GetMapping("/list")
    public String showList(Model model) {
        model.addAttribute("list", service.getList());
        return "spchitiet/index";
    }
    @GetMapping("/add")
    public String addForm(){
        return "spchitiet/form";
    }
    @PostMapping("/add")
    public String addSPChiTiet(SPChiTiet spchitiet){
        String nguoiTao = "Admin";
        spchitiet.setNguoiTao(nguoiTao);
        service.add(spchitiet);
        return "redirect:/spchitiet/index/list";
    }
    @GetMapping("/detail/{id}")
    public String showDetail(@PathVariable("id") Integer id, Model model){
        model.addAttribute("spchitiet",service.findById(id));
        return "spchitiet/detail";
    }
    @GetMapping("/update/{id}")
    public String showUpdate(@PathVariable("id") Integer id,Model model){
        model.addAttribute("spchitiet",service.findById(id));
        return "spchitiet/update";
    }
    @PostMapping("/update")
    public String update(SPChiTiet spChiTiet){
        SPChiTiet existingSPChiTiet = service.findById(spChiTiet.getId());

        if (existingSPChiTiet != null) {
            // Cập nhật các trường cần thiết
            existingSPChiTiet.setMa(spChiTiet.getMa());
            existingSPChiTiet.setIdSanPham(spChiTiet.getIdSanPham());
            existingSPChiTiet.setIdThuongHieu(spChiTiet.getIdThuongHieu());
            existingSPChiTiet.setIdChatLieuVo(spChiTiet.getIdChatLieuVo());
            existingSPChiTiet.setIdLoaiMu(spChiTiet.getIdLoaiMu());
            existingSPChiTiet.setIdKichThuoc(spChiTiet.getIdKichThuoc());
            existingSPChiTiet.setIdKhuyenMai(spChiTiet.getIdKhuyenMai());
            existingSPChiTiet.setIdLoaiKinh(spChiTiet.getIdLoaiKinh());
            existingSPChiTiet.setIdChatLieuDem(spChiTiet.getIdChatLieuDem());
            existingSPChiTiet.setIdMauSac(spChiTiet.getIdMauSac());
            existingSPChiTiet.setSl(spChiTiet.getSl());
            existingSPChiTiet.setTt(spChiTiet.getTt());
            existingSPChiTiet.setXuatXu(spChiTiet.getXuatXu());
//            existingSPChiTiet.setDonGia(spChiTiet.getDonGia());
            // Giữ nguyên ngaytao
            // Cập nhật ngaycapnhat
            existingSPChiTiet.setNgayCapNhat(LocalDateTime.now());

            service.update(existingSPChiTiet);
        }
        return "redirect:/spchitiet/index/list";
    }
    @GetMapping("/delete")
    public String deleteSPChiTiet(@RequestParam("id") Integer id){
        service.delete(id);
        return "redirect:/spchitiet/index/list";
    }
    @ModelAttribute("listSanPham")
    List<SanPham> getListSP() {
        return sanPhamService.getList();
    }
    @ModelAttribute("listKichThuoc")
    List<KichThuoc> getListKT() {
        return kichThuocService.getList();
    }
    @ModelAttribute("listMauSac")
    List<MauSac> getListMS() {
        return mauSacService.getList();
    }
    @ModelAttribute("listThuongHieu")
    List<ThuongHieu> getListTH() {
        return thuongHieuService.getList();
    }
    @ModelAttribute("listChatLieuVo")
    List<ChatLieuVo> getListCLV() {
        return chatLieuVoService.getList();
    }
    @ModelAttribute("listLoaiMu")
    List<LoaiMu> getListLM() {
        return loaiMuService.getList();
    }
    @ModelAttribute("listKhuyenMai")
    List<KhuyenMai> getListKM() {
        return khuyenMaiService.getList();
    }
    @ModelAttribute("listLoaiKinh")
    List<LoaiKinh> getListLK() {
        return loaiKinhService.getList();
    }
    @ModelAttribute("listChatLieuDem")
    List<ChatLieuDem> getListCLD() {
        return chatLieuDemService.getList();
    }
}