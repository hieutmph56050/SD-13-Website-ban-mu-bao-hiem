package com.example.saferide.controller;


import com.example.saferide.entity.HoaDon;
import com.example.saferide.entity.HoaDonChiTiet;
import com.example.saferide.entity.SPChiTiet;
import com.example.saferide.entity.TaiKhoan;
import com.example.saferide.repository.HoaDonChiTietRepository;
import com.example.saferide.repository.HoaDonRepository;
import com.example.saferide.repository.SPChiTietRepository;
import com.example.saferide.repository.TaiKhoanRepository;
import com.example.saferide.request.ThanhToanRequest;
import com.example.saferide.request.ThemSanPhamRequest;
import com.example.saferide.response.InvoiceResponse;
import com.example.saferide.response.ProductInInvoiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ban-hang")
public class BanHangController {

    @Autowired
    private HoaDonRepository hoaDonRepository;

    @Autowired
    private HoaDonChiTietRepository hoaDonChiTietRepository;

    @Autowired
    private SPChiTietRepository spChiTietRepository;

    @Autowired
    private TaiKhoanRepository taiKhoanRepository;

    @PostMapping("/them-hoa-don")
    public ResponseEntity<?> themHoaDon(HoaDon hoaDon, @RequestBody(required = false) String tenKhachHang, @RequestBody(required = false) String sdt) {
        String timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMdHms"));
        LocalDateTime fakedata = LocalDateTime.now();
        hoaDon.setMa("HD" + timeStamp);
        TaiKhoan taiKhoan = taiKhoanRepository.findById(1).orElse(null);
        hoaDon.setIdTaiKhoan(taiKhoan);
        hoaDon.setIdVoucher(null);
        hoaDon.setLoaiHoaDon("Hóa đơn Tại Quầy");
        hoaDon.setNgayGiaoHang(fakedata);
        hoaDon.setNgayNhan(fakedata);
        hoaDon.setGiaGiam(null);
        hoaDon.setTongTien(new BigDecimal("0"));
        hoaDon.setSoTienDaTra(new BigDecimal("0"));
        hoaDon.setGhiChu("Don Hang Ban Tai Quay");
        hoaDon.setDiaChi("SafeRide - Hà Nội");
        hoaDon.setTt("Chưa Thanh Toan");
        hoaDon.setNguoiTao(taiKhoan != null ? taiKhoan.getTen() : "Nhan Vien 1");
        hoaDon.setNgayTao(LocalDateTime.now());
        HoaDon hoaDonSaved = hoaDonRepository.save(hoaDon);
//        themSanPham(hoaDon.getId(), 1, new BigDecimal("1"), model);
        return ResponseEntity.ok(hoaDonSaved);
    }

    @GetMapping("/hoa-don/{maHoaDon}")
    public ResponseEntity<?> selectInvoice(@PathVariable String maHoaDon) {
        HoaDon hoaDon = hoaDonRepository.findByMaHoaDon(maHoaDon).orElseThrow(() -> new RuntimeException("Hóa đơn không tồn tại"));
        InvoiceResponse<HoaDon> response = new InvoiceResponse<>();
        if (response.data == null) {
            response.data = new ArrayList<>();
        }
        response.data.add(0, hoaDon);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/hoa-don/{maHoaDon}/sanpham")
    public ResponseEntity<?> productInInvoice(@PathVariable String maHoaDon) {
        HoaDon hoaDon = hoaDonRepository.findByMaHoaDon(maHoaDon).orElseThrow(() -> new RuntimeException("Hóa đơn không tồn tại"));
        ProductInInvoiceResponse<HoaDonChiTiet> response = new ProductInInvoiceResponse<>();
        response.data = hoaDonChiTietRepository.findByHoaDonId(hoaDon.getId());
        return ResponseEntity.ok(response);
    }


    @PostMapping("/them-san-pham")
    public ResponseEntity<?> themSanPham(@RequestBody ThemSanPhamRequest request) {
        String maHoaDon = request.getMaHoaDon();
        Integer sanPhamId = request.getSanPhamId();
        BigDecimal soLuong = request.getSoLuong();

        // Retrieve the invoice by maHoaDon
        HoaDon hoaDon = hoaDonRepository.findByMaHoaDon(maHoaDon).orElseThrow(() -> new RuntimeException("Hóa đơn không tồn tại"));

        // Retrieve the product details
        SPChiTiet spChiTiet = spChiTietRepository.findById(sanPhamId).orElseThrow(() -> new RuntimeException("Sản phẩm không tồn tại"));

        // Check if the product already exists in the invoice detail
        Optional<HoaDonChiTiet> existingHDCT = hoaDonChiTietRepository.findByHoaDonIdAndSanPhamId(hoaDon.getId(), sanPhamId);

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

            BigDecimal newTotal = spChiTiet.getDonGia().multiply(soLuong);
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

        // Return the updated invoice details
        return ResponseEntity.ok(hoaDonChiTietRepository.findByHoaDonId(hoaDon.getId()));
    }

    private void updateTotalAmount(HoaDon hoaDon) {
        List<HoaDonChiTiet> chiTietList = hoaDonChiTietRepository.findByHoaDonId(hoaDon.getId());

        // Sử dụng BigDecimal để tính tổng tiền
        BigDecimal totalAmount = chiTietList.stream().map(HoaDonChiTiet::getTongTien).reduce(BigDecimal.ZERO, BigDecimal::add); // Cộng dồn các giá trị BigDecimal
        hoaDon.setTongTien(totalAmount);
        hoaDonRepository.save(hoaDon);
    }


    @DeleteMapping("/xoa/hdct/{id}/{hoaDonId}")
    public ResponseEntity<?> remove(@PathVariable Integer id, @PathVariable Integer hoaDonId) {
        // Check if the invoice detail exists
        HoaDonChiTiet hoaDonChiTiet = hoaDonChiTietRepository.findById(id).orElseThrow(() -> new RuntimeException("Hóa đơn chi tiết không tồn tại"));

        // Remove the invoice detail
        hoaDonChiTietRepository.deleteById(id);

        // Update the total amount for the invoice
        HoaDon hoaDon = hoaDonRepository.findById(hoaDonId).orElseThrow(() -> new RuntimeException("Hóa đơn không tồn tại"));
        updateTotalAmount(hoaDon);

        // Redirect to the invoice details page
        return ResponseEntity.ok("Xóa thành công <3");
    }

    @PostMapping("/thanh-toan")
    public ResponseEntity<?> thanhToan(@RequestBody ThanhToanRequest request) {
        String maHD = request.getMaHoaDon();
        BigDecimal soTienKhachTra = request.getSoTienKhachTra();

        // Kiểm tra mã hóa đơn
        if (maHD == null) {
            return ResponseEntity.badRequest().body("Hóa đơn không tồn tại. Vui lòng chọn hóa đơn hợp lệ.");
        }

        // Lấy hóa đơn và chi tiết hóa đơn
        HoaDon hoaDon = hoaDonRepository.findByMaHoaDon(maHD).orElseThrow(() -> new RuntimeException("Hóa đơn không tồn tại"));

        List<HoaDonChiTiet> listHoaDonChiTiet = hoaDonChiTietRepository.findByHoaDonId(hoaDon.getId());

        // Kiểm tra xem hóa đơn có sản phẩm không
        if (listHoaDonChiTiet.isEmpty()) {
            return ResponseEntity.badRequest().body("Hóa đơn này không có sản phẩm nào. Vui lòng thêm sản phẩm trước khi thanh toán.");
        }

        // Kiểm tra số tiền khách trả

        if (soTienKhachTra.compareTo(hoaDon.getTongTien()) < 0) {
            return ResponseEntity.badRequest().body("Số tiền khách trả không đủ. Vui lòng kiểm tra lại.");
        }

        // Xử lý thanh toán
        hoaDon.setTt("Đã thanh toán");
        hoaDon.setSoTienDaTra(soTienKhachTra);
        hoaDonRepository.save(hoaDon);

        // Cập nhật tồn kho sản phẩm
        for (HoaDonChiTiet hdct : listHoaDonChiTiet) {
            SPChiTiet spChiTiet = hdct.getIdSPCT();

            if (spChiTiet.getSl() < hdct.getSl()) {
                return ResponseEntity.badRequest().body("Số lượng tồn kho không đủ cho sản phẩm: " + spChiTiet.getIdSanPham().getTen());
            }

            spChiTiet.setSl(spChiTiet.getSl() - hdct.getSl());
            spChiTietRepository.save(spChiTiet);
        }
        return ResponseEntity.ok("Thanh toán thành công");
    }

}