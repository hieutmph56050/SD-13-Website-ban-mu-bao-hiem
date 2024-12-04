package com.example.saferide.controller;

import com.example.saferide.dto.Token;
import com.example.saferide.entity.TaiKhoan;
import com.example.saferide.service.CustomUserDetailsService;
import com.example.saferide.service.TaiKhoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class LoginController {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    public BCryptPasswordEncoder passwordEncoder;

    @Autowired
    TaiKhoanService taiKhoanService;

    @GetMapping("/login")
    public ResponseEntity<?> login(@RequestBody TaiKhoan taiKhoan) {
        try {
            return ResponseEntity.ok().body(Token.builder().token(taiKhoanService.login(taiKhoan)).build());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @PostMapping("/dang-ki")
    public ResponseEntity<?> dangKi(@RequestBody TaiKhoan taiKhoan) {
        try {
            taiKhoanService.dangKi(taiKhoan);
            return ResponseEntity.ok("Dang ki thanh cong");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
