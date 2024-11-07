package com.example.saferide.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.Locale;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "hoadonchitiet")
public class HoaDonChiTiet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JoinColumn(name = "id_hoadon", referencedColumnName = "id")
    @ManyToOne
    private HoaDon idHoaDon;

    @JoinColumn(name = "id_spct", referencedColumnName = "id")
    @ManyToOne
    private SPChiTiet idSPCT;

    @Column(name = "mahdct")
    private String mahdct;

    @Column(name = "tongtien", precision = 18, scale = 2)
    private BigDecimal tongTien = BigDecimal.ZERO;

    @Column(name = "Gia", precision = 18, scale = 2)
    private BigDecimal Gia = BigDecimal.ZERO;

    public String getFormattedTongTien() {
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        return formatter.format(tongTien);
    }

    @Column(name = "soluong")
    private int sl;

    @Column(name = "ghichu")
    private String ghiChu;

//    @Column(name = "Gia", precision = 18, scale = 2)
//    private BigDecimal gia = BigDecimal.ZERO;
//
//    public String getFormattedGia() {
//        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
//        return formatter.format(gia);
//    }

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
