package com.example.saferide.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Locale;

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

    @JoinColumn(name = "ID_TaiKhoan", referencedColumnName = "id")
    @ManyToOne
    private TaiKhoan idTaiKhoan;

    @JoinColumn(name = "id_voucher", referencedColumnName = "id")
    @ManyToOne
    private Voucher idVoucher;

    @Column(name = "loaihoadon" , columnDefinition = "BIT")
    private boolean loaiHoaDon;

    @Column(name = "ngaygiaohang")
    private Date ngayGiaoHang;

    @Column(name = "ngaynhan")
    private Date ngayNhan;

    @Column(name = "giagiam" , precision = 18, scale = 2)
    private BigDecimal giaGiam;

    @Column(name = "tongtien" , precision = 18, scale = 2)
    private BigDecimal tongTien;

    @Column(name = "sotiendatra" , precision = 18, scale = 2)
    private BigDecimal soTienDaTra;

    public String getFormattedGia() {
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        return formatter.format(tongTien);
    }

    @Column(name = "ghichu")
    private String ghiChu;

    @Column(name = "diachi")
    private String diaChi;

    @Column(name = "ngaytao")
    private LocalDateTime ngayTao;

    @Column(name = "nguoitao")
    private String nguoiTao;

    @Column(name = "ngaycapnhat")
    private LocalDateTime ngayCapNhat;

    @Column(name = "nguoicapnhat")
    private String nguoiCapNhat;

    @PrePersist
    protected void onCreate() {
        ngayTao = LocalDateTime.now(); // Gán thời gian hiện tại
    }

    @Column(name = "trangthai")
    private String tt;
}
