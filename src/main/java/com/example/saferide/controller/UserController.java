package com.example.saferide.controller;

import com.example.saferide.entity.*;
import com.example.saferide.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private GioHangRepository gioHangRepository;

    @Autowired
    private SPChiTietRepository spChiTietRepository;

    @Autowired
    private TaiKhoanRepository taiKhoanRepository;

    @Autowired
    private HoaDonRepository hoaDonRepository;

    @Autowired
    private HoaDonChiTietRepository hoaDonChiTietRepository;

    @PreAuthorize("hasRole('User')")
    @DeleteMapping("/xoa/{id}")
    public ResponseEntity<?> xoaGioHang(@PathVariable Integer id) {
        gioHangRepository.deleteById(id);
        return ResponseEntity.ok("Xóa thành công");
    }

    @PreAuthorize("hasRole('User')")
    @PostMapping("/dat-hang")
    public ResponseEntity<?> datHangOnline(@RequestParam Integer gioHangId,
                                           @RequestParam Integer idTaiKhoan,
                                           @RequestParam String soNha,
                                           @RequestParam String xa,
                                           @RequestParam String huyen,
                                           @RequestParam String thanhPho) {
        // Kiểm tra giỏ hàng tồn tại
        Optional<GioHang> gioHangOpt = gioHangRepository.findById(gioHangId);
        if (!gioHangOpt.isPresent()) {
            return ResponseEntity.badRequest().body("Giỏ hàng không tồn tại");
        }

        GioHang gioHang = gioHangOpt.get();

        // Kiểm tra tài khoản tồn tại
        Optional<TaiKhoan> taiKhoanOpt = taiKhoanRepository.findById(idTaiKhoan);
        if (!taiKhoanOpt.isPresent()) {
            return ResponseEntity.badRequest().body("Tài khoản không tồn tại");
        }

        TaiKhoan taiKhoan = taiKhoanOpt.get();

        // Tính tổng tiền giỏ hàng
        BigDecimal tongTien = gioHang.getGioHangChiTietList().stream()
                .map(gioHangChiTiet -> gioHangChiTiet.getDonGia().multiply(BigDecimal.valueOf(gioHangChiTiet.getSl())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Tạo hóa đơn mới
        HoaDon hoaDon = new HoaDon();
        hoaDon.setIdTaiKhoan(taiKhoan);
        hoaDon.setNgayTao(LocalDateTime.now());
        hoaDon.setTongTien(tongTien);
        hoaDon.setLoaiHoaDon("Hóa Đơn Online");
        String diaChi = soNha + ", Xã " + xa + ", Huyện " + huyen + ", " + thanhPho;
        hoaDon.setDiaChi(diaChi);
        hoaDon.setTt("Chưa thanh toán");
        hoaDon.setNguoiTao("Phạm Anh Tuấn");

        // Lưu hóa đơn vào cơ sở dữ liệu để lấy ID
        hoaDonRepository.save(hoaDon);

        // Sau khi lưu, tạo mã hóa đơn từ ID
        String maHD = "HD" + hoaDon.getId();
        hoaDon.setMa(maHD);
        hoaDonRepository.save(hoaDon);

        // Tạo chi tiết hóa đơn
        for (GioHangChiTiet gioHangChiTiet : gioHang.getGioHangChiTietList()) {
            HoaDonChiTiet hoaDonChiTiet = new HoaDonChiTiet();
            hoaDonChiTiet.setIdHoaDon(hoaDon);
            hoaDonChiTiet.setIdSPCT(gioHangChiTiet.getIdSPCT());
            hoaDonChiTiet.setGia(gioHangChiTiet.getDonGia());
            hoaDonChiTiet.setSl(gioHangChiTiet.getSl());
            hoaDonChiTiet.setTt("Chưa thanh toán");
            String maHDCT = "HDCT" + hoaDon.getId();
            hoaDonChiTiet.setMahdct(maHDCT);
            hoaDonChiTiet.setTongTien(gioHangChiTiet.getDonGia().multiply(BigDecimal.valueOf(gioHangChiTiet.getSl())));

            hoaDonChiTietRepository.save(hoaDonChiTiet);
        }

        return ResponseEntity.ok("Đặt hàng thành công, Mã hóa đơn: " + maHD);
    }

    @PreAuthorize("hasRole('User')")
    @PostMapping("/them-san-pham-on")
    public ResponseEntity<?> themSanPhamOnline(Integer hoaDonId, Integer sanPhamId, BigDecimal soLuong, Model model) {
        // Retrieve the selected invoice
        HoaDon hoaDon = hoaDonRepository.findById(hoaDonId).orElseThrow(() -> new RuntimeException("Hóa đơn không tồn tại"));

        if (hoaDonId == null) {
            model.addAttribute("error", "Không có hóa đơn để thêm sản phẩm.");
            List<HoaDon> hoaDonList = hoaDonRepository.findAll();
            model.addAttribute("listHoaDon", hoaDonList);
            model.addAttribute("listSanPhamChiTiet", spChiTietRepository.findAll());
            model.addAttribute("listHoaDonChiTiet", null);  // Đặt giá trị ban đầu là null
        }

        // Retrieve the product details
        SPChiTiet spChiTiet = spChiTietRepository.findById(sanPhamId).orElseThrow(() -> new RuntimeException("Sản phẩm không tồn tại"));

        // Check if the product already exists in the invoice detail
        Optional<HoaDonChiTiet> existingHDCT = hoaDonChiTietRepository.findByHoaDonIdAndSanPhamId(hoaDonId, sanPhamId);

        if (existingHDCT.isPresent()) {
            // If exists, update the quantity and total amount
            HoaDonChiTiet hdct = existingHDCT.get();
            BigDecimal currentQuantity = BigDecimal.valueOf(hdct.getSl());
            hdct.setSl(currentQuantity.add(soLuong).intValue());

            BigDecimal newTotal = spChiTiet.getDonGia().multiply(BigDecimal.valueOf(hdct.getSl()));
            hdct.setTongTien(newTotal);
            hoaDonChiTietRepository.save(hdct);
        } else {
            // Create a new invoice detail
            HoaDonChiTiet hoaDonChiTiet = new HoaDonChiTiet();
            hoaDonChiTiet.setIdHoaDon(hoaDon);
            hoaDonChiTiet.setIdSPCT(spChiTiet);
            hoaDonChiTiet.setSl(soLuong.intValue());
            hoaDonChiTiet.setTongTien(spChiTiet.getDonGia());

            BigDecimal newTotal = spChiTiet.getDonGia().multiply(soLuong);
            if (newTotal.compareTo(new BigDecimal("99999999.99")) > 0) {
                model.addAttribute("error", "Tổng tiền vượt quá giới hạn cho phép.");
//                return selectInvoice(hoaDonId, model);
            }
            hoaDonChiTiet.setTongTien(newTotal);

            // Generate mã for the new invoice detail
            String maHDCT = "HDCT-" + hoaDon.getId() + "-" + (hoaDonChiTietRepository.countByHoaDonId(hoaDon.getId()) + 1);
            hoaDonChiTiet.setMahdct(maHDCT);
            hoaDonChiTiet.setTt("Đang thanh toán");
            // Save the invoice detail
            hoaDonChiTietRepository.save(hoaDonChiTiet);

        }

        // Update total amount for the invoice
        updateTotalAmount(hoaDon);

        // Redirect to display the invoice details
        return ResponseEntity.ok(hoaDonChiTietRepository);
    }

    private void updateTotalAmount(HoaDon hoaDon) {
        List<HoaDonChiTiet> chiTietList = hoaDonChiTietRepository.findByHoaDonId(hoaDon.getId());

        // Sử dụng BigDecimal để tính tổng tiền
        BigDecimal totalAmount = chiTietList.stream().map(HoaDonChiTiet::getTongTien).reduce(BigDecimal.ZERO, BigDecimal::add); // Cộng dồn các giá trị BigDecimal
        hoaDon.setTongTien(totalAmount);
        hoaDonRepository.save(hoaDon);
    }

}
