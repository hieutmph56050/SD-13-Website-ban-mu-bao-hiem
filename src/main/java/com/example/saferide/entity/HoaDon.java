package com.example.saferide.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

    @Column(name = "tongtien")
    private int tongTien;

    public String getFormattedGia() {
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        return formatter.format(tongTien);
    }

    @Column(name = "diachi")
    private String diaChi;

    @Column(name = "ngaytao")
    private LocalDateTime ngayTao;


    @PrePersist
    protected void onCreate() {
        ngayTao = LocalDateTime.now(); // Gán thời gian hiện tại
    }

    @Column(name = "trangthai", columnDefinition = "BIT")
    private String tt;
}
