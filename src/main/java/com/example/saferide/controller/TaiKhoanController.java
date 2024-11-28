package com.example.saferide.controller;

import com.example.saferide.dto.DoiMatKhauDTO;
import com.example.saferide.entity.TaiKhoan;
import com.example.saferide.repository.TaiKhoanRepository;
import com.example.saferide.service.TaiKhoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tai-khoan")
public class TaiKhoanController {

    @Autowired
    private TaiKhoanRepository taiKhoanRepository;

    @Autowired
    TaiKhoanService service;

    @GetMapping("/hien-thi")
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(service.getList());
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody TaiKhoan taiKhoan){
        return ResponseEntity.ok(service.add(taiKhoan));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody TaiKhoan taiKhoan){
        return ResponseEntity.ok(service.update(taiKhoan,id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){
        return ResponseEntity.ok(service.delete(id));
    }
    @GetMapping("{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id){
        return ResponseEntity.ok(service.findById(id));
    }

    @PutMapping("/trang-thai")
    public ResponseEntity<?> thayDoiTrangThai(@RequestParam("id") Integer id, @RequestParam("trangThai") Boolean trangThai) {
        // Tìm tài khoản theo ID
        TaiKhoan taiKhoan = taiKhoanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tài khoản với ID: " + id));

        // Cập nhật trạng thái
        taiKhoan.setTt(trangThai);
        taiKhoanRepository.save(taiKhoan);

        // Trả về phản hồi
        return ResponseEntity.ok("Trạng thái tài khoản đã được thay đổi thành: " + trangThai);
    }

//
//    @PostMapping("/change-password")
//    public ResponseEntity<?> changePassword(@RequestBody DoiMatKhauDTO request, Authentication authentication) {
//        String tenDangNhap = authentication.getName(); // Lấy tên đăng nhập từ JWT hoặc session
//
//        try {
//            service.changePassword(tenDangNhap, request.getMatKhauCu(), request.getMatKhauMoi());
//            return ResponseEntity.ok("Password changed successfully");
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }

}