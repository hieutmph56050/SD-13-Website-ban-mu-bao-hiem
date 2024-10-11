package com.example.demo.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import jakarta.persistence.PrePersist;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "khuyenmai")
public class KhuyenMai {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "makhuyenmai")
    private String ma;

    @Column(name = "tenkhuyenmai")
    private String ten;

    @Column(name = "giatrikhuyenmai", precision = 18, scale = 2)
    private BigDecimal giaTri;

    @Column(name = "ngaybatdau")
    private LocalDateTime ngayBD;

    @Column(name = "ngayketthuc")
    private LocalDateTime ngayKT;

    @Column(name = "phuongthuckhuyenmai")
    private String PTKM;

    @Column(name = "dieukienkhuyenmai")
    private String DKKM;

    @Column(name = "nguoitao")
    private String nguoiTao;

    @Column(name = "nguoicapnhat")
    private String nguoiCapNhat;

    @Column(name = "ngaytao")
    private LocalDateTime ngayTao;

    @Temporal(TemporalType.TIMESTAMP)
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

    @Column(name = "trangthai")
    private String tt;
}
