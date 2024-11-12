package com.example.saferide.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "giohangchitiet")
public class GioHangChiTiet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JoinColumn(name = "id_giohang", referencedColumnName = "id")
    @ManyToOne
    private GioHang idGioHang;

    @JoinColumn(name = "ID_SPCT", referencedColumnName = "id")
    @ManyToOne
    private SPChiTiet idSPCT;

    @Column(name = "ma")
    private String ma;

    @Column(name = "dongia", precision = 18, scale = 2)
    private BigDecimal donGia;

    @Column(name = "soluong")
    private int sl;

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

    @Column(name = "trangthai")
    private String tt;
}
