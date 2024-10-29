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
@Table(name = "sanphamchitiet")
public class SPChiTiet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "MaSPCT")
    private String ma;

    @ManyToOne
    @JoinColumn(name = "id_sp",referencedColumnName = "id")
    private SanPham idSanPham;

    @JoinColumn(name = "id_thuonghieu", referencedColumnName = "id")
    @ManyToOne
    private ThuongHieu idThuongHieu;

    @JoinColumn(name = "id_chatlieuvo", referencedColumnName = "id")
    @ManyToOne
    private ChatLieuVo idChatLieuVo;

    @JoinColumn(name = "id_loaimu", referencedColumnName = "id")
    @ManyToOne
    private LoaiMu idLoaiMu;

    @JoinColumn(name = "id_kichthuoc", referencedColumnName = "id")
    @ManyToOne
    private KichThuoc idKichThuoc;

    @JoinColumn(name = "id_khuyenmai", referencedColumnName = "id")
    @ManyToOne
    private KhuyenMai idKhuyenMai;

    @JoinColumn(name = "id_loaikinh", referencedColumnName = "id")
    @ManyToOne
    private LoaiKinh idLoaiKinh;

    @JoinColumn(name = "id_chatlieudem", referencedColumnName = "id")
    @ManyToOne
    private ChatLieuDem idChatLieuDem;

    @JoinColumn(name = "id_mausac", referencedColumnName = "id")
    @ManyToOne
    private MauSac idMauSac;

    @Column(name = "soluong")
    private int sl;
  
    @Column(name = "DonGia", precision = 10, scale = 2)
   private BigDecimal donGia;

    @Column(name = "motachitiet")
    private String moTaCT;

    public String getFormattedGia() {
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        return formatter.format(donGia);
    }

    @Column(name = "anh")
    private String anh;

    @Column(name = "trangthai")
    private String tt;

    @Column(name = "xuatxu")
    private String xuatXu;

    @Column(name = "nguoitao")
    private String nguoiTao;

    @Column(name = "ngaytao")
    private LocalDateTime ngayTao;

    @Column(name = "nguoicapnhat")
    private String nguoiCapNhat;

//    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T:'HH:mm")
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
