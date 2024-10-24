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
@Table(name = "thanhtoanchitiet")
public class ThanhToan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_hoadon" , referencedColumnName = "id")
    private HoaDon idHoaDon;

    @Column(name = "tenthanhtoan")
    private String ten;

    @Column(name = "tongtien", precision = 18, scale = 2)
    private BigDecimal tongTien;

    @Column(name = "ngaythanhtoan")
    private LocalDateTime ngayThanhToan;

    @Column(name = "nguoitao")
    private String nguoiTao;

    @Column(name = "nguoicapnhat")
    private String nguoiCapNhat;

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