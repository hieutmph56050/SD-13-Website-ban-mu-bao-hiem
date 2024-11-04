package com.example.saferide.controller;

import com.example.saferide.entity.GioHang;
import com.example.saferide.entity.GioHangChiTiet;
import com.example.saferide.entity.SPChiTiet;
import com.example.saferide.entity.TaiKhoan;
import com.example.saferide.repository.GioHangChiTietRepository;
import com.example.saferide.repository.GioHangRepository;
import com.example.saferide.repository.SPChiTietRepository;
import com.example.saferide.repository.TaiKhoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> themSanPham(@RequestParam Integer productId, @RequestParam Integer idTaiKhoan) {
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

        // Add product to cart
        GioHangChiTiet gioHangChiTiet = new GioHangChiTiet();
        gioHangChiTiet.setIdGioHang(gioHang);
        gioHangChiTiet.setIdSPCT(spChiTiet);
        gioHangChiTiet.setMa(spChiTiet.getMa());
        gioHangChiTiet.setDonGia(spChiTiet.getDonGia());
        gioHangChiTiet.setTt("Đang tạo");
        gioHangChiTietRepository.save(gioHangChiTiet);

        return ResponseEntity.ok("Sản phẩm đã thêm vào giỏ hàng");
    }

    @PostMapping("/thanh-toan")
    public ResponseEntity<?> thanhToanOnline (){

        return ResponseEntity.ok("Thanh toán thành công ");
    }

}
