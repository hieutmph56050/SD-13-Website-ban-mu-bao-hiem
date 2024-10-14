//package com.example.saferide.controller;
//
//import com.example.saferide.entity.HoaDon;
//import com.example.saferide.entity.TaiKhoan;
//import com.example.saferide.entity.Voucher;
//import com.example.saferide.service.HoaDonService;
//import com.example.saferide.service.TaiKhoanService;
//import com.example.saferide.service.VoucherService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//@Controller
//@RequestMapping("/hoadon/index")
//public class HoaDonController {
//    @Autowired
//    HoaDonService service;
//
//    @Autowired
//    TaiKhoanService taikhoanService;
//
//    @Autowired
//    VoucherService voucherService;
//
//    @GetMapping("/list")
//    public String getList(Model model){
//        model.addAttribute("list",service.getList());
//        return "hoadon/index";
//    }
//    @GetMapping("/add")
//    public String addForm() {
//        return "hoadon/form";
//    }
//    @PostMapping("/add")
//    public String add(HoaDon hoadon) {
//        String nguoiTao = "Admin";
//        hoadon.setNguoiTao(nguoiTao);
//        service.add(hoadon);
//        return "redirect:/hoadon/index/list";
//    }
//    @GetMapping("/detail/{id}")
//    public String detail(@PathVariable("id") Integer id,Model model) {
//        model.addAttribute("hoadon",service.findById(id));
//        return "hoadon/detail";
//    }
//    @GetMapping("/update/{id}")
//    public String showUpdate(@PathVariable("id") Integer id, Model model){
//        model.addAttribute("hoadon",service.findById(id));
//        return "hoadon/update";
//    }
//    @PostMapping("/update")
//    public String update(HoaDon hoaDon){
//        HoaDon existingHoaDon = service.findById(hoaDon.getId());
//
//        if (existingHoaDon != null) {
//            // Cập nhật các trường cần thiết
//            existingHoaDon.setMa(hoaDon.getMa());
//            existingHoaDon.setIdTaiKhoan(hoaDon.getIdTaiKhoan());
//            existingHoaDon.setIdVoucher(hoaDon.getIdVoucher());
//            existingHoaDon.setNgayGiaoHang(hoaDon.getNgayGiaoHang());
//            existingHoaDon.setNgayNhan(hoaDon.getNgayNhan());
//            existingHoaDon.setGiaGiam(hoaDon.getGiaGiam());
//            existingHoaDon.setTongTien(hoaDon.getTongTien());
//            existingHoaDon.setDiaChi(hoaDon.getDiaChi());
//            existingHoaDon.setTt(hoaDon.getTt());
//            // Giữ nguyên ngaytao
//            // Cập nhật ngaycapnhat
//            existingHoaDon.setNgayCapNhat(LocalDateTime.now());
//
//            service.update(existingHoaDon);
//        }
//        return "redirect:/hoadon/index/list";
//    }
//    @ModelAttribute("listTaiKhoan")
//    List<TaiKhoan> getListTaiKhoan() { return taikhoanService.getList();
//    }
//    @ModelAttribute("listVoucher")
//    List<Voucher> getListVoucher() {
//        return voucherService.getList();
//    }
//}