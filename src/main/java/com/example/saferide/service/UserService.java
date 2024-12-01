package com.example.saferide.service;

import com.example.saferide.entity.TaiKhoan;
import com.example.saferide.repository.TaiKhoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final TaiKhoanRepository taiKhoanRepository;

    @Autowired
    public UserService(PasswordEncoder passwordEncoder, TaiKhoanRepository taiKhoanRepository) {
        this.passwordEncoder = passwordEncoder;
        this.taiKhoanRepository = taiKhoanRepository;
    }

    // Register new user
    public void registerUser(TaiKhoan taiKhoan) {
        // Encode the password before saving it
        String encodedPassword = passwordEncoder.encode(taiKhoan.getMatKhau());
        taiKhoan.setMatKhau(encodedPassword); // Set the encoded password
        taiKhoanRepository.save(taiKhoan); // Save the user with encoded password
    }

    // Check if the raw password matches the stored password
    public boolean checkPassword(TaiKhoan taiKhoan, String rawPassword) {
        return passwordEncoder.matches(rawPassword, taiKhoan.getMatKhau());
    }

    // Find a user by their username
    public TaiKhoan findByUsername(String username) {
        return taiKhoanRepository.findByTenDangNhap(username).orElse(null); // Return user or null if not found
    }
}
