package com.example.demo.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "loaikinh")
public class LoaiKinh {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "maloaikinh")
    private String ma;

    @Column(name = "tenloaikinh")
    private String ten;

    @Column(name = "mota")
    private String moTa;

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
