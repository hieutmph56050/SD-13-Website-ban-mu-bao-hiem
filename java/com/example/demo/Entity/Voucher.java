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
@Table(name = "voucher")
public class Voucher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "mavoucher")
    private String ma;

    @Column(name = "tenvoucher")
    private String ten;

    @Column(name = "giatrivoucher", precision = 18, scale = 2)
    private BigDecimal giaTri;

    @Column(name = "giatrivouchertoida", precision = 18, scale = 2)
    private BigDecimal giaTriMax;

    @Column(name = "gioihansoluong")
    private int gioihan;

    @Column(name = "ngaybatdau")
    private LocalDateTime ngayBD;

    @Column(name = "ngayketthuc")
    private LocalDateTime ngayKT;

    @Column(name = "mota")
    private String moTa;

    @Column(name = "trangthai")
    private String tt;
}
