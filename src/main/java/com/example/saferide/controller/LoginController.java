package com.example.saferide.controller;

import com.example.saferide.dto.Token;
import com.example.saferide.entity.GioHang;
import com.example.saferide.entity.TaiKhoan;
import com.example.saferide.repository.GioHangRepository;
import com.example.saferide.repository.TaiKhoanRepository;
import com.example.saferide.service.CustomUserDetailsService;
import com.example.saferide.service.TaiKhoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class LoginController {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    public BCryptPasswordEncoder passwordEncoder;

    @Autowired
    TaiKhoanService taiKhoanService;

    @Autowired
    TaiKhoanRepository taiKhoanRepository;

    @Autowired
    GioHangRepository ghRepo;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody TaiKhoan taiKhoan) {
        try {
            String token = taiKhoanService.login(taiKhoan);
            TaiKhoan authenticatedUser = taiKhoanRepository.findByTenDangNhap(taiKhoan.getTenDangNhap()).orElseThrow(() -> new RuntimeException("Tài khoản không tồn tại"));

            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("user", Map.of("id", authenticatedUser.getId(),
                    "username", authenticatedUser.getTenDangNhap(),
                    "name", authenticatedUser.getTen(),
                    "role", authenticatedUser.getIdVaiTro().getTen()));
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Internal server error"));
        }
    }

    @PostMapping("/dang-ki")
    public ResponseEntity<?> dangKi(@RequestBody TaiKhoan taiKhoan) {
        try {
            taiKhoanService.dangKi(taiKhoan);
            TaiKhoan tk = taiKhoanRepository.findByTenDangNhap(taiKhoan.getTenDangNhap()).orElseThrow(() -> new RuntimeException("Tài khoản không tồn tại"));
            GioHang gioHang = ghRepo.findByIdTaiKhoanGH(tk.getId());
            if (gioHang == null) {
                gioHang = new GioHang();
                gioHang.setId(tk.getId());
                gioHang.setIdTaiKhoan(tk);
                gioHang.setTt(false);
                ghRepo.save(gioHang);
            }

            return ResponseEntity.ok(ghRepo.findByIdTaiKhoanGH(tk.getId()).getId());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
