package com.example.saferide.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "taikhoan")
public class TaiKhoan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_vaitro")
    private VaiTro idVaiTro;

    @Column(name = "tendangnhap")
    private String tenDangNhap;

    @Column(name = "matkhau")
    private String matKhau;

    @Column(name = "hoten")
    private String ten;

    @Column(name = "ngaysinh")
    private LocalDate ngaySinh;

    @Column(name = "gioitinh", columnDefinition = "BIT")
    private boolean gioiTinh;

    @Column(name = "sdt")
    private String sdt;

    @Column(name = "email")
    private String email;

    @Column(name = "cccd")
    private String cccd;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "Trangthai", columnDefinition = "BIT")
    private boolean tt;

    @Column(name = "ngaytao")
    private LocalDateTime ngayTao;

    //    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Column(name = "ngaycapnhat")
    private LocalDateTime ngayCapNhat;

    @PrePersist
    protected void onCreate() {
        ngayTao = LocalDateTime.now(); // Gán thời gian hiện tại
    }

    @PreUpdate
    protected void onUpdate() {
        ngayCapNhat = LocalDateTime.now(); // Gán thời gian hiện tại khi cập nhật
    }
}
