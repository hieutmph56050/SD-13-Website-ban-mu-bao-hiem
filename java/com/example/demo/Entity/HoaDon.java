package com.example.demo.Entity;

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
@Table(name = "hoadon")
public class HoaDon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "mahoadon")
    private String ma;

    @JoinColumn(name = "id_taikhoan", referencedColumnName = "id")
    @ManyToOne
    private TaiKhoan idTaiKhoan;

    @JoinColumn(name = "id_voucher", referencedColumnName = "id")
    @ManyToOne
    private Voucher idVoucher;

    @Column(name = "ngaygiaohang")
    private LocalDate ngayGiaoHang;

    @Column(name = "ngaynhan")
    private LocalDate ngayNhan;

    @Column(name = "giagiam")
    private int giaGiam;

    @Column(name = "tongtien")
    private int tongTien;

    @Column(name = "sotiendatra")
    private int soTienDaTra;

    @Column(name = "ghichu")
    private String ghiChu;

    @Column(name = "diachi")
    private String diaChi;

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

    @Column(name = "trangthai", columnDefinition = "BIT")
    private String tt;
}
