package com.example.demo.Entity;

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
    private String ma;

    @Column(name = "tongtien", precision = 18, scale = 2)
    private BigDecimal tongTien;

    @Column(name = "soluong")
    private int sl;

    @Column(name = "ghichu")
    private String ghiChu;

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
