package com.example.demo.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sanpham")
public class SanPham {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

//    @Column(name = "id_sanphamchitiet")
//    private String idSPCT;

    @Column(name = "ten")
    private String ten;

    @Column(name = "mota")
    private String moTa;

    @Column(name = "trangthai")
    private String tt;

//    @Column(name = "ngaytao")
//    private LocalDateTime ngayTao;
//
////    @Temporal(TemporalType.TMESTAMP)
//    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
//    @Column(name = "ngaycapnhat")
//    private LocalDateTime ngayCapNhat;
//
//    @PrePersist
//    protected void onCreate() {
//        ngayTao = LocalDateTime.now(); // Gán thời gian hiện tại
//    }
//    @PreUpdate
//    protected void onUpdate() {
//        ngayCapNhat = LocalDateTime.now(); // Gán thời gian hiện tại khi cập nhật
//    }
}
