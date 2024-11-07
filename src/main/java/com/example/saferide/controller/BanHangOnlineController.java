package com.example.saferide.controller;

import com.example.saferide.entity.*;
import com.example.saferide.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/online")
public class BanHangOnlineController {

    @Autowired
    private GioHangRepository gioHangRepository;

    @Autowired
    private GioHangChiTietRepository gioHangChiTietRepository;

    @Autowired
    private SPChiTietRepository spChiTietRepository;

    @Autowired
    private TaiKhoanRepository taiKhoanRepository;

    @Autowired
    private HoaDonRepository hoaDonRepository;

    @Autowired
    private HoaDonChiTietRepository hoaDonChiTietRepository;

    @DeleteMapping("/xoa/{id}")
    public ResponseEntity<?> xoaGioHang(@PathVariable Integer id){
        gioHangRepository.deleteById(id);
        return ResponseEntity.ok("Xóa thành công");
    }

    @GetMapping("/hien-thi")
    public ResponseEntity<?> hienThiGioHang(){
        return ResponseEntity.ok(gioHangChiTietRepository.findAll());
    }

    @PostMapping("/them-san-pham")
    public ResponseEntity<?> themSanPham(@RequestParam Integer productId,
                                         @RequestParam Integer idTaiKhoan,
                                         @RequestParam int soLuong) {
        // Find product by ID
        SPChiTiet spChiTiet = spChiTietRepository.findById(productId).orElse(null);
        if (spChiTiet == null) {
            return ResponseEntity.badRequest().body("Sản phẩm chi tiết không tồn tại");
        }

        // Find account by ID
        TaiKhoan taiKhoan = taiKhoanRepository.findById(idTaiKhoan).orElse(null);
        if (taiKhoan == null) {
            return ResponseEntity.badRequest().body("Tài khoản không tồn tại");
        }

        // Find or create cart
        GioHang gioHang = gioHangRepository.findByIdTaiKhoanGH(idTaiKhoan);
        if (gioHang == null) {
            gioHang = new GioHang();
            gioHang.setIdTaiKhoan(taiKhoan);
            gioHang.setTt(false);
            gioHangRepository.save(gioHang);
        }

        Optional<GioHangChiTiet> existingGioHangChiTiet = gioHangChiTietRepository
                .findByIdGioHangAndIdSPCT(gioHang, spChiTiet);


        if (soLuong <= 0) {
            return ResponseEntity.badRequest().body("Số lượng sản phẩm phải lớn hơn 0");
        }

        if (existingGioHangChiTiet.isPresent()) {
            // Nếu sản phẩm đã có trong giỏ hàng, cập nhật số lượng
            GioHangChiTiet gioHangChiTiet = existingGioHangChiTiet.get();
            gioHangChiTiet.setSl(gioHangChiTiet.getSl() + soLuong);
            gioHangChiTietRepository.save(gioHangChiTiet);
        } else {
            // Nếu sản phẩm chưa có, thêm sản phẩm mới vào giỏ hàng
            GioHangChiTiet gioHangChiTiet = new GioHangChiTiet();
            gioHangChiTiet.setIdGioHang(gioHang);
            gioHangChiTiet.setIdSPCT(spChiTiet);
            gioHangChiTiet.setMa(spChiTiet.getMa());
            gioHangChiTiet.setDonGia(spChiTiet.getDonGia());
            gioHangChiTiet.setSl(soLuong);  // Đặt số lượng
            gioHangChiTiet.setTt("Đang tạo");
            gioHangChiTietRepository.save(gioHangChiTiet);
        }
        return ResponseEntity.ok("Sản phẩm đã thêm vào giỏ hàng");
    }

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
        hoaDon.setLoaiHoaDon(Integer.parseInt("Hóa Đơn Online"));
        String diaChi = soNha + ", Xã " + xa + ", Huyện " + huyen + ", " + thanhPho;
        hoaDon.setDiaChi(diaChi);
        hoaDon.setTt("Chưa thanh toán");  // Đơn hàng chưa thanh toán
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

    @PostMapping("/thanh-toan")
    public ResponseEntity<?> thanhToanOnline(@RequestParam Integer hoaDonId) {
        // Kiểm tra hóa đơn tồn tại
        Optional<HoaDon> hoaDonOpt = hoaDonRepository.findById(hoaDonId);
        if (!hoaDonOpt.isPresent()) {
            return ResponseEntity.badRequest().body("Hóa đơn không tồn tại");
        }

        HoaDon hoaDon = hoaDonOpt.get();

        // Kiểm tra trạng thái hóa đơn (phải là "Chưa thanh toán")
        if (!hoaDon.getTt().equals("Chua thanh toán")) {
            return ResponseEntity.badRequest().body("Hóa đơn đã được thanh toán hoặc không hợp lệ");
        }

        // Thực hiện logic thanh toán (có thể tích hợp với cổng thanh toán)
        // Ví dụ: Kiểm tra số dư tài khoản, thực hiện chuyển tiền, ...

        // Cập nhật trạng thái hóa đơn sau khi thanh toán thành công
        hoaDon.setTt("Đã thanh toán");
        hoaDonRepository.save(hoaDon);

        return ResponseEntity.ok("Thanh toán thành công cho hóa đơn: " + hoaDon.getMa());
    }


}
